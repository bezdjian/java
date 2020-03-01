import React from 'react';
import './App.css';

function SearchBox() {
  return (
    <div className="App">
      <input className="form-control" id="searchField" placeholder="Search stops by line number" type="text" />
        <div className="input-group-append">
          <button className="btn btn-primary" id="search-btn" type="button">Go</button>
        </div>
        <div className="alert alert-danger" id="errorMessageDialog">
          { /* Error msg here when input is wrong */ }
        </div>
      <div id="search-results">
        <ul className="list-group" id="stopNamesUL">
          { /* results here or separate component? */ }
        </ul>
      </div>
    </div>
);
}

export default SearchBox;
