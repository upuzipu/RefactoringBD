import {configureStore} from "@reduxjs/toolkit"
import loginSlice from "./loginSlice";
import playlistSlice from "./playlistSlice";
import albumSlice from "./albumSlice";

const store = configureStore({
    reducer: {
        login: loginSlice.reducer,
        album: albumSlice.reducer,
        playlist: playlistSlice.reducer
    }
})

export default store;