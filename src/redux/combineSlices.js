import {combineSlices} from "@reduxjs/toolkit";
import loginSlice from "./loginSlice";
import albumSlice from "./albumSlice";
import playlistSlice from "./playlistSlice";


const rootReducer = combineSlices(loginSlice, albumSlice, playlistSlice)

export default rootReducer;