import React, {Component} from 'react';
import {trackPromise} from "react-promise-tracker";
import Popup from "./Popup";

class SearchBox extends Component {

  constructor(props) {
    super(props);
    this.state = {
      result: [],
      searchValue: '',
      showPopup: false,
      popupMsg: ''
    };

    this.handleChange = this.handleChange.bind(this);
    this.togglePopup = this.togglePopup.bind(this)
  }

  togglePopup() {
    this.setState({
      showPopup: !this.state.showPopup
    });
  }

  handleChange(event) {
    this.setState({searchValue: event.target.value});
  }

  search() {
    const API_URL = process.env.REACT_APP_TRAFIKLAB_URL;
    const getStops_url = API_URL + '/api/getStops/';
    if (this.state.searchValue === "") {
      this.setState({
        ...this.state,
        showPopup: true,
        popupMsg: 'Type in a buss number'
      })
    } else {
      trackPromise(
        fetch(getStops_url + this.state.searchValue)
          .then(res => res.json())
          .then((data) => {
            this.setState({
              result: data
            });
          })
          .catch(console.log))
        .then(r => console.log("TrackPromise Search: ", r));
    }
  }

  render() {
    return (
      <div>
        <div className="input-group mb-3">
          <input className="form-control"
                 id="searchField" placeholder="Search stops by line number"
                 type="text"
                 value={this.state.searchValue}
                 onChange={this.handleChange}/>
          <div className="input-group-append">
            <button className="btn btn-primary"
                    id="search-btn"
                    type="button"
                    onClick={() => this.search()}>
              Go
            </button>
          </div>
        </div>
        <div className="card-body">
          <ul className="list-group" id="stopNamesUL">
            {this.state.result && this.state.result.map((stops) => (
              <li key={stops.stopName}>{stops.stopName}</li>
            ))}
          </ul>
        </div>
        {this.state.showPopup ?
          <Popup text={this.state.popupMsg}
                 closePopup={this.togglePopup.bind(this)}
          />
          : null
        }
      </div>
    );
  }
}

export default SearchBox;
