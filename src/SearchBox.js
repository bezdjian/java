import React, { Component } from "react";
import { trackPromise } from "react-promise-tracker";
import Popup from "./Popup";

class SearchBox extends Component {
  constructor(props) {
    super(props);
    this.state = {
      result: [],
      searchField: "",
      error: "",
      showPopup: false,
    };

    this.doSearch = this.doSearch.bind(this);
    this.onSearchChange = this.onSearchChange.bind(this);
    this.togglePopup = this.togglePopup.bind(this);
  }

  togglePopup = () => {
    this.setState({ showPopup: !this.state.showPopup });
  };

  onSearchChange = (e) => {
    // Clear error msg
    this.setState({ error: "" });
    // If Enter pressed
    if (e.keyCode === 13) {
      this.doSearch(e.target.value);
    } else {
      this.setState({
        searchField: e.target.value,
      });
    }
  };

  doSearch = (e) => {
    const API_URL = process.env.REACT_APP_TRAFIKLAB_URL;
    const getStops_url = API_URL + "/api/getStops/" + this.state.searchField;
    console.log("Calling ", getStops_url);
    trackPromise(
      fetch(getStops_url)
        .then((res) => {
          console.log("res: ", res);
          if (res.status === 404)
            this.setState({
              ...this.state,
              showPopup: true,
              popupMsg: res.status + ": Search did not give any result",
            });
          if (res.status !== 200 && res.status !== 404)
            this.setState({
              ...this.state,
              showPopup: true,
              popupMsg: res.status + ": Error accured while searching",
            });

          if (res.ok) return res.json();
        })
        .then((data) => {
          if (data.length === 0) {
            this.setState({
              ...this.state,
              showPopup: true,
              popupMsg:
                "There are no bus stops for bus numer " +
                this.state.searchField,
            });
          } else {
            this.setState({
              result: data,
            });
          }
        })
        .catch((r) => {
          console.log("Error while searching: ", r);
        })
    );
  };

  render() {
    return (
      <div className="searchContainer">
        <div className="row">
          <div className="col-8 pr-0">
            <input
              className="form-control border-radious-0"
              id="searchField"
              placeholder="Search stops by line number"
              type="text"
              onChange={(e) => this.onSearchChange(e)}
              onKeyDown={(e) => this.onSearchChange(e)}
            />
          </div>
          <div className="col-4 pl-0">
            <button
              className="btn btn-primary w-100 border-0 border-radious-0"
              id="search-btn"
              type="button"
              onClick={(e) => this.doSearch(e)}
            >
              Go
            </button>
          </div>
        </div>

        <div className="row">
          <div className="col-12">
            {this.state.error ? (
              <div className="alert alert-danger mt-2" id="errorMessageDialog">
                {this.state.error}
              </div>
            ) : (
              <div id="search-results" className="p-5">
                <ul className="list-group" id="stopNamesUL">
                  {this.state.result &&
                    this.state.result.map((stops, k) => (
                      <li key={k}>{stops.stopName}</li>
                    ))}
                </ul>
              </div>
            )}
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

export default SearchBox;
