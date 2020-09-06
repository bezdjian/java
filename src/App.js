import React, { Component } from "react";
import TopTen from "./components/BussStops/TopTen";
import TopTenStopNames from "./components/BussStops/TopTenStopNames";
import SearchBox from "./components/SearchBox";
import { trackPromise } from "react-promise-tracker";
import Popup from "./components/Popup";

import "./css/bootstrap.min.css";
import "./css/App.css";

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      stopNames: [],
      topTenList: [],
      showPopup: false,
      popupMsg: "",
    };

    this.togglePopup = this.togglePopup.bind(this);
  }

  togglePopup = () => {
    this.setState({ showPopup: !this.state.showPopup });
  };

  componentDidMount() {
    const API_URL = process.env.REACT_APP_TRAFIKLAB_URL;
    const service_url = API_URL + "/api/getMostStops";

    console.log("** Calling: ", service_url);
    trackPromise(
      fetch(service_url)
        .then((res) => res.json())
        .then((data) => {
          this.setState({
            stopNames: data.stopNames,
            topTenList: data.topTenList,
          });
        })
        .catch((error) => {
          console.log("-Error: ", error);
          this.setState({ 
            showPopup: true, 
            popupMsg: '' + error,
          });
        })
    );
  }

  render() {
    return (
      <div className="App">
        <div className="container-fluid p-3">
          <div className="row content">
            <div className="col-sm-4 text-left" id="left-pane">
              <h4>Top 10 lines have most bus stops</h4>
              <hr />
              <div id="top-ten">
                <ul key="top-ten">
                  <TopTen topTenList={this.state.topTenList} />
                </ul>
              </div>
            </div>

            <div className="col-sm-4 text-left">
              <h4>Stop names of the line who has the most stops</h4>
              <hr />
              <div id="top-ten-stopnames">
                <ul
                  className="list-group"
                  id="top-ten-stopNamesUL"
                  key="top-ten-stopNames"
                >
                  <TopTenStopNames stopNames={this.state.stopNames} />
                </ul>
              </div>
            </div>

            <div className="col-sm-4 text-left">
              <SearchBox />
            </div>
          </div>
        </div>
        {this.state.showPopup ? (
          <Popup
            text={this.state.popupMsg}
            closePopup={this.togglePopup.bind(this)}
          />
        ) : null}
      </div>
    );
  }
}

export default App;
