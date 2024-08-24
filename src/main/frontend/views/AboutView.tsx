import React from 'react';

const AboutView: React.FC = () => {
    return (
        <div className="about-page">
            <h1>About Live Score App ⚽</h1>
            <p>
                Welcome to the Live Score App! This application provides real-time updates on football matches
                from various leagues around the world. Stay up-to-date with your favorite teams and leagues
                with our easy-to-use interface.
            </p>
            <h2>Features 🌟</h2>
            <ul>
                <li>Real-time match updates ⏱️</li>
                <li>League-based filtering 🏆</li>
                <li>Detailed match information 📊</li>
                <li>League overview 📅</li>
            </ul>
            <h2>Technologies Used 🛠️</h2>
            <ul>
                <li>React ⚛️</li>
                <li>TypeScript 📝</li>
                <li>Spring Boot 🌱</li>
                <li>Hilla 🌐</li>
            </ul>
            <h2>Links 🔗</h2>
            <ul>
                <li>
                    <a href="https://github.com/miliariadnane/live-score-reactive-spring-boot" target="_blank" rel="noopener noreferrer">
                        GitHub Repository 📂
                    </a>
                </li>
                <li>
                    <a href="https://www.linkedin.com/in/miliariadnane" target="_blank" rel="noopener noreferrer">
                        Creator's LinkedIn 💼
                    </a>
                </li>
                <li>
                    <a href="https://twitter.com/miliariadnane" target="_blank" rel="noopener noreferrer">
                        Creator's Twitter 🐦
                    </a>
                </li>
            </ul>
        </div>
    );
};

export default AboutView;
