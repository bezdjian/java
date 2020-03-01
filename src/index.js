import React from 'react';
import ReactDOM from 'react-dom';
import './css/index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';
import {usePromiseTracker} from 'react-promise-tracker';
import Loader from 'react-loader-spinner';

const LoadingIndicator = () => {
  const {promiseInProgress} = usePromiseTracker();

  return (
    promiseInProgress &&
    <div
      style={{
        width: "100%",
        height: "100%",
        position: "absolute",
        top: 0,
        left: 0,
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: "#000000",
        opacity: 0.7
      }}
    >
      <Loader type="ThreeDots" color="#2BAD60" width="100" height="100"/>
    </div>
  );
};

ReactDOM.render(
  <div>
    <App/>
    <LoadingIndicator/>
  </div>,
  document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
