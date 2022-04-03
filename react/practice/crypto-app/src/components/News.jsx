import React, {useState, useEffect} from 'react'
import {Select, Typography, Row, Col, Avatar, Card} from 'antd';
import moment from 'moment';

import {useGetCryptoNewsQuery} from '../services/cryptoNewsApi';
import {useGetCryptosQuery} from '../services/cryptoApi';

const {Text, Title} = Typography;
const {Option} = Select;
const demoImage = 'https://www.bing.com/th?id=OVFT.mpzuVZnv8dwIMRfQGPbOPC&pid=News';

function News({simplified}) {
    const [newsCategory, setNewsCategory] = useState('Cryptocurrency');

    const {data: cryptoNews} = useGetCryptoNewsQuery({
        newsCategory,
        count: (simplified ? 6 : 30),
    });

    const {data} = useGetCryptosQuery(50);

    if (!cryptoNews ?. value) return 'Loading...';

    return (
        <>
            <Row gutter={[24, 24]}>
                {
                    !simplified && 
                    <Col span={24}>
                        <Select 
                            showSearch
                            className="select-news"
                            placeholder="select a crypto"
                            optionFilterProp="children"
                            onChange={(value) => setNewsCategory(value)}
                            filterOption={(input, option) => option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0}
                        >
                            <Option value="Cryptocurrency">Cryptocurrency</Option>
                            {
                                data ?. data ?.coins.map((coin) => (
                                    <Option value={coin.name}>{coin.name}</Option>
                                ))
                            }
                        </Select>
                    </Col>
                }
                {
                    cryptoNews.value.map((news, i) => (
                        <Col xs={24} sm={12} lg={8} key={i}>
                            <Card hoverable className="news-card">
                                <a href={news.url} target="_blank" rel="norefferrer">
                                    <div className="news-image-container">
                                        <Title className="news-title" level={4}>
                                            {news.name}
                                        </Title>
                                        <img src={news ?. image ?. thumbnail ?. contentUrl || demoImage} alt="" style={{height: "100px", width: "100px"}}/>
                                    </div>
                                    <p>{news.description.length > 100 ? `${news.description.substring(0, 100)}... click to read more` : news.description}</p>
                                    <div className="provider-container">
                                        <div>
                                            <Avatar src={news.provider[0] ?.image ?.thumbnail ?.contentUrl || demoImage}/>
                                            <Text className="provider-name">{news.provider[0]?.name}</Text>
                                        </div>
                                        <Text>{moment(news.datePublished).startOf('ss').fromNow()}</Text>
                                    </div>
                                </a>
                            </Card>
                        </Col>
                    ))
                }
            </Row>
        </>
    )
}

export default News
