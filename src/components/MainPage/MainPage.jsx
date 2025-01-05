import React, {useEffect, useState} from "react";
import axios from 'axios';
import "./MainPage.css"
import {useDispatch, useSelector} from "react-redux";
import {setPlaylist} from "../../redux/playlistSlice";
import {setAlbum} from "../../redux/albumSlice";
import {useNavigate} from "react-router-dom";

export function MainPage() {
    const [albums, setAlbums] = useState([]);
    const [playlists, setPlaylists] = useState([]);
    const username = useSelector((state) => state.login.value)
    const dispatcher = useDispatch()
    const navigate = useNavigate()
    const navigateToAlbum = () => {
        navigate("/album")
    }

    const navigateToPlaylist = () => {
        navigate("/playlist")
    }


    // useEffect(() => {
    //     axios
    //         .get("https://localhost:8080/albums?user=" + username.toString())
    //         .then((response) => {
    //             setAlbums(response.data);
    //             getAlbums()
    //         })
    //         .catch((error) => {
    //             console.log(error);
    //         });
    //     axios
    //         .get("https://localhost:8080/playlists?user=" + username.toString())
    //         .then((response) => {
    //             setPlaylists(response.data);
    //             getPlaylists()
    //         })
    //         .catch((error) => {
    //             console.log(error);
    //         });
    // }, []);

    function getPlaylists() {
        let newUl = document.createElement('ul')
        playlists.forEach(playlist => {
            let listItem = document.createElement("li");
            let itemText = document.createTextNode(playlist.name)
            listItem.appendChild(itemText)
            newUl.appendChild(listItem)
        })
        let playlists_div = document.querySelector('#playlists')
        playlists_div.appendChild(newUl)
    }

    function getAlbums() {
        let newUl = document.createElement('ul')
        albums.forEach(album => {
            let listItem = document.createElement("li");
            let itemText = document.createTextNode(album.name)
            listItem.appendChild(itemText)
            newUl.appendChild(listItem)
        })
        let album_div = document.querySelector('#albums')
        album_div.appendChild(newUl)
    }

    const redirectPlaylist = event => {
        event.preventDefault()
        const target = event.target;
        if (target.tagName === 'SPAN') {
            dispatcher(setPlaylist(target.innerText));
        }
        navigateToPlaylist()
    }

    const redirectAlbum = event => {
        event.preventDefault()
        const target = event.target;
        console.log(target.tagName)
        if (target.tagName === 'LI') {
            dispatcher(setAlbum(target.innerText));
        }
        navigateToAlbum()
    }

    return (
        <div>
            <h1 className="MainText">Welcome back, {username}</h1>
            <div className="wrapper">
                <div className="left">
                    <h1>Your Albums</h1>
                    <button>Add album</button>
                    <div id="albums">
                        <ul>
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
                        <ul>
                            <li onClick={redirectPlaylist}><span>5</span></li>
                            <li onClick={redirectPlaylist}><span>6</span></li>
                            <li onClick={redirectPlaylist}><span>7</span></li>
                            <li onClick={redirectPlaylist}><span>8</span></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default MainPage

