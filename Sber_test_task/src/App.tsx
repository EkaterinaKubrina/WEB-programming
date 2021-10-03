import React from 'react';
import './App.css';
        import { Container } from '@sberdevices/plasma-web';
        import { Row } from '@sberdevices/plasma-web';
        import { Col } from '@sberdevices/plasma-web';
        import { Button,Checkbox, Badge } from '@sberdevices/plasma-web';
        import { HeaderTitleWrapper } from '@sberdevices/plasma-ui';
        import { HeaderRoot } from '@sberdevices/plasma-ui';
        import { HeaderSubtitle, HeaderContent } from '@sberdevices/plasma-ui';
        import { HeaderTitle } from '@sberdevices/plasma-ui';
        import { Card, CardBody, CardMedia, CardContent, TextBox, TextBoxBigTitle, TextBoxBiggerTitle } from '@sberdevices/plasma-ui';
        import { TextBoxSubTitle, CardParagraph1 } from '@sberdevices/plasma-ui';
        import { body1, headline1, headline4, button1 } from '@sberdevices/plasma-tokens';

        import { createGlobalStyle } from 'styled-components';
        import { darkEva } from '@sberdevices/plasma-tokens/themes';
        import { text, background, gradient } from '@sberdevices/plasma-tokens';

        var DocStyles = createGlobalStyle`
        html {
        color: ${text};
        background-color: ${background};
        background-image: ${gradient};

        /** необходимо залить градиентом всю подложку */
        min-height: 100vh;
        }
        `;

        var Theme = createGlobalStyle(darkEva);



        function App() {

  return (<>
    {<Container>
    <HeaderRoot>
        <HeaderTitleWrapper>
            <HeaderSubtitle style={headline1}>Сериалы</HeaderSubtitle>
            <HeaderTitle style={body1}>Какой посмотреть?</HeaderTitle>
        </HeaderTitleWrapper>
    <HeaderContent>
        <Button onPress={() => this.props.navigation.goBack()} >Выход</Button>
    </HeaderContent>
</HeaderRoot>

<div style={{ display: 'flex' }}>

<Card style={{ width: '22.5rem', marginLeft: '4rem', marginTop: '2rem', marginBottom: '2rem'  }}>
<CardBody>
<CardMedia
        src='/nyanya.jpg'
        placeholder='/nyanya.jpg'
        ratio="1 / 1"
/>
<CardContent cover>
    <TextBox>
        <TextBoxBigTitle>Моя прекрасная няня</TextBoxBigTitle>
        <TextBoxSubTitle>7 сезонов</TextBoxSubTitle>
        <CardParagraph1 mt="6x" lines={4}> российский комедийный телесериал, снятый по мотивам американского сериала «Няня».
    </CardParagraph1>
</TextBox>
</CardContent>
        </CardBody>
        </Card>


<Card style={{ width: '22.5rem', marginLeft: '0.75rem', marginTop: '2rem', marginBottom: '2rem'  }}>
<CardBody>
<CardMedia
        src='/haus.jpg'
        placeholder='/haus.jpg'
        ratio="1 / 1"
/>
<CardContent cover>
    <TextBox>
        <TextBoxBigTitle>Доктор Хаус</TextBoxBigTitle>
        <TextBoxSubTitle>8 сезонов</TextBoxSubTitle>
        <CardParagraph1 mt="6x" lines={4}> американский телесериал о выдающемся враче-диагносте Грегори Хаусе и его команде.
    </CardParagraph1>
</TextBox>
</CardContent>
        </CardBody>
        </Card>

<Card style={{ width: '22.5rem', marginLeft: '0.75rem', marginTop: '2rem', marginBottom: '2rem' }}>
<CardBody>
<CardMedia
        src='/vmest.jpg'
        placeholder='/vmest.jpg'
        ratio="1 / 1"
/>
<CardContent cover>
    <TextBox>
        <TextBoxBigTitle>Счастливы вместе</TextBoxBigTitle>
        <TextBoxSubTitle>6 сезонов</TextBoxSubTitle>
        <CardParagraph1 mt="6x" lines={4}>
        комедийный телесериал, российская адаптация американского телесериала «Женаты… с детьми»
    </CardParagraph1>
</TextBox>
</CardContent>
        </CardBody>
        </Card>
        </div>
<div style={{ display: 'flex' }}>

<Card style={{ width: '22.5rem', marginLeft: '4rem', marginTop: '2rem', marginBottom: '2rem'  }}>
<CardBody>
<CardMedia
        src='/bess.jpg'
        placeholder='/bess.jpg'
        ratio="1 / 1"
/>
<CardContent cover>
    <TextBox>
        <TextBoxBigTitle>Бесстыжие</TextBoxBigTitle>
        <TextBoxSubTitle>11 сезонов</TextBoxSubTitle>
        <CardParagraph1 mt="6x" lines={4}> американский телесериал, выходивший на канале Showtime адаптацией одноимённого британского сериала Лос-Анджелесе.
    </CardParagraph1>
</TextBox>
</CardContent>
        </CardBody>
        </Card>


<Card style={{ width: '22.5rem', marginLeft: '0.75rem', marginTop: '2rem', marginBottom: '2rem'  }}>
<CardBody>
<CardMedia
        src='/friend.jpg'
        placeholder='/friend.jpg'
        ratio="1 / 1"
/>
<CardContent cover>
    <TextBox>
        <TextBoxBigTitle>Друзья</TextBoxBigTitle>
        <TextBoxSubTitle>10 сезонов</TextBoxSubTitle>
        <CardParagraph1 mt="6x" lines={4}> американский комедийный телесериал, повествующий о жизни шестерых друзей.
    </CardParagraph1>
</TextBox>
</CardContent>
        </CardBody>
        </Card>

<Card style={{ width: '22.5rem', marginLeft: '0.75rem', marginTop: '2rem', marginBottom: '2rem' }}>
<CardBody>
<CardMedia
        src='/scr.jpg'
        placeholder='/scr.jpg'
        ratio="1 / 1"
/>
<CardContent cover>
    <TextBox>
        <TextBoxBigTitle>Клиника</TextBoxBigTitle>
        <TextBoxSubTitle>9 сезонов</TextBoxSubTitle>
        <CardParagraph1 mt="6x" lines={4}>
        американский комедийно-драматический телевизионный сериал, посвящённый работе и жизни молодых врачей.
    </CardParagraph1>
</TextBox>
</CardContent>
        </CardBody>
        </Card>
        </div>

<Row>
<Col size={3} offset={5}>
<Badge  style={headline4} text="Хочу посмотреть:" size="l" />

<Checkbox  style={{marginTop: '1rem'}}  label="Друзья"  />
<Checkbox label="Клиника"  />
<Checkbox label="Моя прекрасная няня"  />
<Checkbox label="Счастливы вместе" />
<Checkbox label="Доктор Хаус" />
<Checkbox label="Бесстыжие"  />

</Col>
        </Row>
    </Container>



        }
    <DocStyles />
    <Theme />
</>



  );
}


export default App;
