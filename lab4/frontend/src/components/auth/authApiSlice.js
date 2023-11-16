import { apiSlice } from "../../app/api/apiSlice";

export const authApiSlice = apiSlice.injectEndpoints({
    endpoints: builder => ({
        login: builder.mutation({
            query: credentials => ({
                url: '/auth/login',
                method: 'POST',
                body: { ...credentials }
            }),
            onError: (error) => {
                if (error.status === 401) {
                    throw new Error('Check name and password.');
                }
                throw new Error('Error');
            }
        }),
        registration: builder.mutation({
            query: credentials => ({
                url: '/auth/registration',
                method: 'POST',
                body: { ...credentials }
            }),
            onError: (error) => {
                if (error.status === 401) {
                    throw new Error('Check name and password.');
                }
                throw new Error('Error');
            }
        }),
    })
});

export const {
    useLoginMutation,
    useRegistrationMutation
} = authApiSlice;
