import React, {Component} from 'react';
import {trackPromise} from "react-promise-tracker";

class SearchBox extends Component {
  state = {
    result: []
  };

  search() {
    // TODO: Get the search value from text field.
    const API_URL = process.env.REACT_APP_TRAFIKLAB_URL;
    const getStops_url = API_URL + '/api/getStops/' + 172;
    trackPromise(
      fetch(getStops_url)
        .then(res => res.json())
        .then((data) => {
          this.setState({
            result: data
          });
        })
        .catch(console.log)).then(r => console.log("RRRRR: ", r));
  }

  render() {
    return (
      <div className="input-group mb-3">
        <input className="form-control" id="searchField" placeholder="Search stops by line number" type="text"/>
        <div className="input-group-append">
          <button className="btn btn-primary"
                  id="search-btn"
                  type="button"
                  onClick={() => this.search()}>
            Go
          </button>
        </div>
        <div className="alert alert-danger" id="errorMessageDialog">
          { /* Error msg here when input is wrong */}
        </div>
        <div id="search-results">
          <ul className="list-group" id="stopNamesUL">
            {this.state.result && this.state.result.map((stops) => (
              <li>{stops.stopName}</li>
            ))}
          </ul>
        </div>
      </div>
    );
  }

}

export default SearchBox;
