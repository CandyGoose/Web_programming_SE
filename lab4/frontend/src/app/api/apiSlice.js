import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react";

import {logout, setTokens} from "../../components/auth/authSlice";

const baseQuery = fetchBaseQuery({
    baseUrl: process.env.REACT_APP_API_BASE_URL,
    credentials: 'include',
    prepareHeaders: (headers, { getState }) => {
        const token = getState().auth.accessToken
        if (token) headers.set("Authorization", `Bearer ${token}`)
        return headers
    }
})

const baseQueryWithReAuth = async (args, api, extraOptions) => {
    let result = await baseQuery(args, api, extraOptions)

    if (result?.error?.status === 401) {
        const refreshResult = await baseQuery(
            {
                url: '/auth/refresh',
                method: 'POST',
                body: {refresh: extractRefreshToken()}
            },
            api,
            extraOptions
        )

        if (refreshResult?.data) {
            const { access: accessToken, refresh: refreshToken } = refreshResult.data
            api.dispatch(setTokens({ accessToken, refreshToken }))
            result = await baseQuery(args, api, extraOptions)
        } else {
            api.dispatch(logout())
        }
    }

    return result
}

const extractRefreshToken = () => {
    const rootJSON = window.localStorage.getItem("persist:root")
    const root = JSON.parse(rootJSON)
    const authJSON = root.auth
    const auth = JSON.parse(authJSON)
    return auth.refreshToken
}

export const apiSlice = createApi({
    reducerPath: 'apiSlice',
    baseQuery: baseQueryWithReAuth,
    endpoints: builder => ({})
})