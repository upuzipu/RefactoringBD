import { createSlice } from '@reduxjs/toolkit'

const albumSlice = createSlice({
    name: 'album',
    initialState: {
        value: null
    },
    reducers: {
        setAlbum(state, action) {
            state.value = action.payload
        }
    }
})

export const { setAlbum } = albumSlice.actions

export default albumSlice