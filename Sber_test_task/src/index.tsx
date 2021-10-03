import React from 'react';
import ReactDOM from 'react-dom';
        import { DeviceThemeProvider } from '@sberdevices/plasma-ui';
        import { GlobalStyle } from './GlobalStyle';
        import App from './App';

        ReactDOM.render(
<DeviceThemeProvider>
  <App />
  <GlobalStyle />
</DeviceThemeProvider>,
        document.getElementById('root'),
        );

