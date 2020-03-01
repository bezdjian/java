import React from 'react';

const TopTen = ({topTenList}) => {
  console.log("topTenList---; ", topTenList);
  return (
    <div className="App">
      {topTenList && topTenList.map((lineNumber,stopCount) => (
        <ul>{lineNumber}</ul>
      ))}
    </div>
  );
};

export default TopTen;
