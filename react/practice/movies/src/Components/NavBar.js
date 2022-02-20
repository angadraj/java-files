import React, { Component } from 'react';
import {Link} from 'react-router-dom';

export default class NavBar extends Component {
    render() {
        return (
        <div style={{display:"flex", alignItems: "center", justifyContent: "space-around"}}>
            <Link to="/"><h1>Movies App</h1></Link>
            <Link to="/favourites"><h1>Favorites</h1></Link>
        </div>
        )
    }
}
