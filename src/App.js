import React, {Component} from 'react';
import TopTen from './TopTen';
import TopTenStopNames from './TopTenStopNames';
import SearchBox from "./SearchBox";
import {trackPromise} from "react-promise-tracker";

import './bootstrap.min.css';
import './css/App.css';

class App extends Component {

  state = {
    stopNames: [],
    topTenList: []
  };

  componentDidMount() {
    const API_URL = process.env.REACT_APP_TRAFIKLAB_URL;
    const getMostStops_url = API_URL + '/api/getMostStops';

    trackPromise(
      fetch(getMostStops_url)
        .then(res => res.json())
        .then((data) => {
          this.setState({
            stopNames: data.stopNames,
            topTenList: data.topTenList
          });
        })
        .catch(console.log))
      .then(r => console.log("TrackPromise getMostStops: ", r));
  }

  render() {
    return (
      <div className="App">
        <div className="container-fluid p-3">
          <div className="row content">

            <div className="col-sm-4 text-left" id="left-pane">
              <h4>Top 10 lines have most bus stops</h4>
              <hr/>
              <div id="top-ten">
                <ul key='top-ten'>
                  <TopTen topTenList={this.state.topTenList}/>
                </ul>
              </div>
            </div>

            <div className="col-sm-4 text-left">
              <h4>Stop names of the line who has the most stops</h4>
              <hr/>
              <div id="top-ten-stopnames">
                <ul className="list-group" id="top-ten-stopNamesUL" key='top-ten-stopNames'>
                  <TopTenStopNames stopNames={this.state.stopNames}/>
                </ul>
              </div>
            </div>

            <div className="col-sm-4 text-left">
              <SearchBox/>
            </div>
          </div>
        </div>
      </div>
    );
  }

}

export default App;
