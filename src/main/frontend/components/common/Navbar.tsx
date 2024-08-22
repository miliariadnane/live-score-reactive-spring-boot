import React from 'react';
import { Link } from 'react-router-dom';

const Navbar: React.FC = () => (
    <nav className="navbar">
        <div className="navbar-brand">LiveScore</div>
        <div className="navbar-menu">
            <Link to="/" className="navbar-item">Home</Link>
            <Link to="/leagues" className="navbar-item">Leagues</Link>
            <Link to="/about" className="navbar-item">About</Link>
        </div>
    </nav>
);

export default Navbar;
