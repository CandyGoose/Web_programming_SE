import {createSlice} from "@reduxjs/toolkit";

const authSlice = createSlice({
    name: 'auth',
    initialState: {
        accessToken: null,
        refreshToken: null,
        user: null
    },
    reducers: {
        setTokens: (state, action) => {
            const { accessToken, refreshToken, user } = action.payload;
            if (!localStorage.getItem("redux-persist localStorage test")) {
                localStorage.removeItem("redux-persist localStorage test");
            }
            const localStorageKeysCount = Object.keys(localStorage).length;

            if (localStorageKeysCount <= 2) {
                state.accessToken = accessToken;
                state.refreshToken = refreshToken;
                state.user = user;

                const tokenParts = accessToken.split('.');
                const payloadObject = JSON.parse(atob(tokenParts[1]));

                if (!localStorage.getItem(payloadObject.user_id) && localStorageKeysCount <= 1) {
                    localStorage.setItem(payloadObject.user_id, Date.now());
                } else {
                    if (!localStorage.getItem(payloadObject.user_id) && localStorageKeysCount === 2) {
                        state.accessToken = null;
                        state.refreshToken = null;
                        state.user = null;
                    }
                }
            }
        },
        setUsername: (state, action) => {
            state.user = action.payload;
        },
        logout: (state) => {
            const token = state.accessToken;
            state.accessToken = null;
            state.refreshToken = null;
            state.user = null;

            if (token) {
                const tokenParts = token.split('.');
                const payloadObject = JSON.parse(atob(tokenParts[1]));

                if (payloadObject.user_id) {
                    localStorage.removeItem(payloadObject.user_id);
                }

                if (window.location.pathname !== '/login') {
                    window.location.reload();
                }
            }
        }
    }
})

export const { setTokens, setUsername, logout } = authSlice.actions;

export default authSlice.reducer
export const selectAccessToken = (state) => state.auth.accessToken
export const selectRefreshToken = (state) => state.auth.refreshToken
export const selectUsername = (state) => state.auth.user;
