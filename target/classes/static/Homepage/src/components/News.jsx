import React, { useState, useEffect } from 'react'
import News1 from "../assets/News/News1.jpg"

const News = () => {
  const [showMore, setShowMore] = useState(false);
  const [newsItems, setNewsItems] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch('/api/news')
      .then(res => res.json())
      .then(data => {
        setNewsItems(data);
        setLoading(false);
      })
      .catch(err => {
        console.error('Failed to fetch news:', err);
        setLoading(false);
      });
  }, []);

  if (loading) {
    return (
      <div className='news'>
        <h2 className='news-title'>Tin Tức</h2>
        <p style={{ textAlign: 'center' }}>Đang tải...</p>
      </div>
    );
  }

  if (newsItems.length === 0) {
    return (
      <div className='news'>
        <h2 className='news-title'>Tin Tức</h2>
        <p style={{ textAlign: 'center' }}>Chưa có tin tức nào.</p>
      </div>
    );
  }

  const displayItem = showMore ? newsItems : newsItems.slice(0, 2);

  return (
    <div className='news'>
      <h2 className='news-title'>Tin Tức</h2>
      {displayItem.map((item, index) => (
        <section className={index % 2 !== 0 ? "news__container flex-row-reverse" : "news__container"} key={item.id}>
          <div className={index % 2 !== 0 ? "news__container-content text-end" : "news__container-content"}>
            <h3 className='news__container-title'>{item.title}</h3>
            <p>{item.content}</p>
          </div>
          <div className="img-border">
            <div className="img-shape">
              <img src={News1} alt={item.title} className='img' />
            </div>
          </div>
        </section>
      ))}
      {newsItems.length > 2 && (
        <h2 className='news-more' onClick={() => setShowMore(!showMore)}>{showMore ? "Thu lại" : "Hiện Thêm"}</h2>
      )}
    </div>
  )
}

export default News