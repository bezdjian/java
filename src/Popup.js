import React from 'react';
import './css/popup.css';

class Popup extends React.Component {
   render() {
    return (
      <div className='popup'>
        <div className='popup_inner'>
          <h3 className="popup_title">{this.props.text}</h3>
          <button className="close_pupup btn btn-dark close_pupup" onClick={this.props.closePopup}>X</button>
        </div>
      </div>
    );
  }
}

export default Popup;