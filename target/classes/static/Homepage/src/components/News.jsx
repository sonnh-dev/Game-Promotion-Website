import React, { useState } from 'react'
import News1 from "../assets/News/News1.jpg"
const News = () => {
  const [showMore , setShowMore] = useState(false);
  const array= [
    {
    id:1,
    image:News1,
    title:"Thêm Nhân vật mới",
    description :"Thêm skin Cappuccino Assassino cho nhân vật",
  },
{
    id:2,
    image:News1,
    title:"Thêm Nhân vật mới",
    description :"Thêm skin Cappuccino Assassino cho nhân vật",
  },
  {
    id:3,
    image:News1,
    title:"Thêm Nhân vật mới",
    description :"Thêm skin Cappuccino Assassino cho nhân vật",
  },
  {
    id:4,
    image:News1,
    title:"Thêm Nhân vật mới",
    description :"Thêm skin Cappuccino Assassino cho nhân vật",
  }
]
const displayItem = showMore ? array : array.slice(0,2);
  return (
    <div className='news'>
          <h2 className='news-title'>Tin Tức</h2>
          {displayItem.map((item)=>(
            <section className={item.id % 2 === 0 ? "news__container flex-row-reverse":"news__container"} key={item.id}>
           <div className={item.id % 2 === 0 ? "news__container-content text-end":"news__container-content"}> 
            <h3 className='news__container-title'>{item.title}</h3>
            <p>{item.description}</p>
           </div>
            <div className="img-border">
              <div className="img-shape">
                <img src={item.image} alt="news_1" className='img'/>
              </div>
           </div>
         </section>
          ))}
          {/* <section className='news__container flex-row-reverse'>
           <div className='news__container-content text-end'> 
            <h3 className='news__container-title'>Thêm nhân vật mới</h3>
            <p>Thêm skin Cappuccino Assassino cho nhân vật </p>
           </div>
            <div className="img-border">
              <div className="img-shape">
                <img src={News1} alt="news_1" className='img'/>
              </div>
           </div>
         </section> */}
         <h2 className='news-more' onClick={()=>setShowMore(!showMore)}>{showMore ? "Thu lại":"Hiện Thêm"}</h2>
    </div>
  )
}

export default News