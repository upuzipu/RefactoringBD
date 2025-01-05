import React from 'react';
import "./EmptyPage.css"
import Freakify from "../../svgs/freakify.svg"
import {useNavigate} from "react-router-dom";


export function EmptyPage() {
    const navigate = useNavigate();
    const navigateToLogin = () => {
        navigate('/login')
    }
    const navigateToRegistration = () => {
        navigate('/registration')
    }

    return (
        <div>
            <div className="header">
                <h1>
                    Freakify
                </h1>
                <img className="freakify" src={Freakify}/>
            </div>
            <div className="btns">
                <div className="login">
                    <button className="login_btn" onClick={navigateToLogin}>
                        Log in
                    </button>
                </div>
                <div className="registration">
                    <button className="registration_btn" onClick={navigateToRegistration}>
                        Registration
                    </button>
                </div>
            </div>
        </div>
    )
}

export default EmptyPage;
