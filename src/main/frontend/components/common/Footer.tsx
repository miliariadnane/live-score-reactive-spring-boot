import React from 'react';

const Footer: React.FC = () => (
    <footer className="footer">
        <div className="footer-content">
            <p>© 2024 LiveScore App. All rights reserved.</p>
            <div className="footer-links">
                <a href="/privacy" className="footer-link">Privacy Policy</a>
                <a href="/terms" className="footer-link">Terms of Service</a>
                <a href="/contact" className="footer-link">Contact Us</a>
            </div>
        </div>
    </footer>
);

export default Footer;
