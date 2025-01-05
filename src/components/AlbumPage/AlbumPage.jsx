import React, {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import "./AlbumPage.css"
import axios from "axios";
import {setPlaylist} from "../../redux/playlistSlice";
import {setAlbum} from "../../redux/albumSlice";
import {useNavigate} from "react-router-dom";

export function AlbumPage() {
    const username = useSelector((state) => state.login.value)
    const albumName = useSelector((state) => state.album.value)
    const dispatcher = useDispatch()

    const navigate = useNavigate()
    const navigateToAlbum = () => {
        navigate("/album")
    }

    const navigateToPlaylist = () => {
        navigate("/playlist")
    }

    function findAlbumByValue(parentId, value) {
        const parent = document.getElementById(parentId);
        const children = parent.children;

        for (let i = 0; i < children.length; i++) {
            if (children[i].textContent === value) {
                return children[i];
            }
        }

        return null;
    }

    const redirectPlaylist = event => {
        const target = event.target;
        if (target.tagName === 'SPAN') {
            dispatcher(setPlaylist(target.innerText));
        }
        navigateToPlaylist()
        event.preventDefault()
    }

    const redirectAlbum = event => {
        const target = event.target;
        if (target.tagName === 'span') {
            console.log(target.innerText)
            dispatcher(setAlbum(target.innerText));
        }
        navigateToAlbum()
        event.preventDefault()
    }

    return (
        <div>
            <div className="background">
                <h1 className="MainText">Welcome back, {username}</h1>
                <div className="wrapper">
                    <div className="left">
                        <h1>Your Albums</h1>
                        <button>Add album</button>
                        <div id="albums">
                            <ul id="albumList">
                                <li onClick={redirectAlbum}><span>1</span></li>
                                <li onClick={redirectAlbum}><span>2</span></li>
                                <li onClick={redirectAlbum}><span>3</span></li>
                                <li onClick={redirectAlbum}><span>4</span></li>
                            </ul>
                        </div>
                    </div>
                    <div className="right">
                        <h1>Your playlists</h1>
                        <button>Add playlist</button>
                        <div id="playlists">
                            <ul id="albumlist">
                                <li onClick={redirectPlaylist}><span>5</span></li>
                                <li onClick={redirectPlaylist}><span>6</span></li>
                                <li onClick={redirectPlaylist}><span>7</span></li>
                                <li onClick={redirectPlaylist}><span>8</span></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default AlbumPage