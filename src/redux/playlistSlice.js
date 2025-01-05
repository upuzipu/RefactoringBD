import { createSlice } from '@reduxjs/toolkit'

const playlistSlice = createSlice({
    name: 'playlist',
    initialState: {
        value: null
    },
    reducers: {
        setPlaylist(state, action) {
            state.value = action.payload
        }
    }
})

export const { setPlaylist } = playlistSlice.actions

export default playlistSlice