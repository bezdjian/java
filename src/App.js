import React from 'react';
import TopTen from './TopTen';
import TopTenStopNames from './TopTenStopNames';
import SearchBox from "./SearchBox";

import './bootstrap.min.css';

import './App.css';

function App() {
  return (
    <div className="App">
      <div className="container-fluid p-3">
        <div className="row content">

          <div className="col-sm-4 text-left" id="left-pane">
            <h4>Top 10 lines have most bus stops</h4>
            <hr />
              <div id="top-ten">
                <TopTen />
              </div>
          </div>

          <div className="col-sm-4 text-left">
            <h4>Stop names of the line who has the most stops</h4>
            <hr />
              <div id="top-ten-stopnames">
                <p id="linenumber"></p>
                <ul className="list-group" id="top-ten-stopNamesUL"></ul>
              </div>
              <TopTenStopNames />
          </div>

          <div className="col-sm-4 text-left">
            <div className="input-group mb-3">
              <SearchBox/>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
export default App;
