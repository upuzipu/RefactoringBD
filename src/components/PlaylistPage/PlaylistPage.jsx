import React, {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import axios from "axios";

export function PlaylistPage() {
    const username = useSelector((state) => state.login.value)
    const playlistName = useSelector((state) => state.playlist.value)
    const chosenPlaylist = findPlaylistByValue("playlistList", playlistName)


    function findPlaylistByValue(parentId, value) {
        const parent = document.getElementById(parentId);
        const children = parent.children;

        for (let i = 0; i < children.length; i++) {
            if (children[i].textContent === value) {
                return children[i];
            }
        }

        return null;
    }

    return (
        <div>
            <div>
                <h1 className="MainText">Welcome back, {username}</h1>
                <div className="wrapper">
                    <div className="left">
                        <h1>Your Albums</h1>
                        <button>Add album</button>
                        <div id="albums">
                            <ul>
                                <li>1</li>
                                <li>2</li>
                                <li>3</li>
                                <li>4</li>
                            </ul>
                        </div>
                    </div>
                    <div className="right">
                        <h1>Your playlists</h1>
                        <button>Add playlist</button>
                        <div id="playlists">
                            <ul id="playlistList">
                                <li>5</li>
                                <li>6</li>
                                <li>7</li>
                                <li>8</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default PlaylistPage