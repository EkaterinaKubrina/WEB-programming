import { createGlobalStyle } from 'styled-components';
        import { web } from '@sberdevices/plasma-tokens-web/typo';
        import { light } from '@sberdevices/plasma-tokens-web/themes';
        import {
        text, // Цвет текста
        background, // Цвет подложки
        gradient, // Градиент
        } from '@sberdevices/plasma-tokens-web';

        const DocumentStyle = createGlobalStyle`
        html {
        color: ${text};
        background-color: ${background};
        background-image: ${gradient};
        }
        `;
        const ThemeStyle = createGlobalStyle(light);
        const TypoStyle = createGlobalStyle(web);

        export const GlobalStyle = () => (
<>
    <DocumentStyle />
    <ThemeStyle />
    <TypoStyle />
</>
        );