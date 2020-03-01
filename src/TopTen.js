import React from 'react';

const TopTen = ({topTenList}) => {
  return (
    <div key='top-names-card'>
      <div key='top-names-card-body' className="card-body">
        {topTenList.map((topTen) => (
          <li key={topTen.lineNumber} className="card-title">
            Line {topTen.lineNumber} has {topTen.stopCount} stops</li>
        ))}
      </div>
    </div>
  );
};

export default TopTen;
