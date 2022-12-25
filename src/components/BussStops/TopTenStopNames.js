import React from 'react';

const TopTenStopNames = ({stopNames, mostStopNamesLineNumber}) => {
  return (
    <div>
      <div className="card-body">
        <h5 id='linenumber' className="card-title">Line #: {mostStopNamesLineNumber}</h5>
        {stopNames.map((stops) => (
          <li key={stops.stopName} className="card-title">{stops.stopName}</li>
        ))}
      </div>
    </div>
  );
};

export default TopTenStopNames;
