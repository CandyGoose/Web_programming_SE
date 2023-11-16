import React from 'react';
import { createRoot } from 'react-dom/client';
import { Provider } from 'react-redux';
import * as PropTypes from "prop-types";
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import { PersistGate } from 'redux-persist/integration/react';

import App from './App';
import {persistor, store} from "./app/store";

const container = document.getElementById('root');
const root = createRoot(container);

BrowserRouter.propTypes = {children: PropTypes.node};
root.render(
    <React.StrictMode>
        <Provider store={store}>
            <PersistGate loading={null} persistor={persistor}>
                <BrowserRouter basename="/lab4">
                    <Routes>
                        <Route path="/*" element={<App />} />
                    </Routes>
                </BrowserRouter>
            </PersistGate>
        </Provider>
    </React.StrictMode>
)