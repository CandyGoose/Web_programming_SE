import {combineReducers, configureStore} from '@reduxjs/toolkit';
import storage from 'redux-persist/lib/storage';
import {
  persistStore,
  persistReducer,
  FLUSH,
  REHYDRATE,
  PAUSE,
  PERSIST,
  PURGE,
  REGISTER,
} from 'redux-persist'

import {apiSlice} from "./api/apiSlice";
import authReducer from '../components/auth/authSlice'

const reducers = combineReducers({
  [apiSlice.reducerPath]: apiSlice.reducer,
  auth: authReducer
})

const persistConfig = {
  key: 'root',
  version: 1,
  storage: storage,
  whitelist: ['auth']
}

const persistedReducer = persistReducer(persistConfig, reducers)

export const store = configureStore({
  reducer: persistedReducer,
  middleware: getDefaultMiddleware =>
      getDefaultMiddleware({
        // Redux persist
        serializableCheck: {
          ignoredActions: [FLUSH, REHYDRATE, PAUSE, PERSIST, PURGE, REGISTER],
        },
      })
      .concat(apiSlice.middleware),
  devTools: true
})

export const persistor = persistStore(store)
