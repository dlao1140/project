import React from 'react'
import './style.css'

//         component: Footer 레이어웃웃        //
export default function Footer() {

    //         event handler: 인스타 아이콘 버튼 클릭 이벤츠 처리        //
    const onInstaIconButtonClickHandler = () => {
        window.open('https://www.instagram.com');
    }
    //         event handler: 네이버 블로그 아이콘 버튼 클릭 이벤츠 처리        //
    const onNaverIconButtonClickHandler = () => {
        window.open('https://blog.naver.com');
    }
    
//         render: Footer 레이어웃 렌더링        //
  return (
    <div id='footer'>
        <div className='footer-container'>
            <div className='footer-top'>
                <div className='footer-logo-box'>
                    <div className='icon-box'>
                        <div className='icon logo-light-icon'></div>
                    </div>
                    <div className='footer-logo-text'>{'Hoons Board'}</div>
                </div>
                <div className='footer-link-box'>
                    <div className='footer-email-link'>{'qor0860@naver.com'}</div>
                    <div className='icon-button' onClick={onInstaIconButtonClickHandler}>
                        <div className='icon insta-icon'></div>
                    </div>
                    <div className='icon-button' onClick={onNaverIconButtonClickHandler}>
                        <div className='icon naver-blog-icon'></div>
                    </div>
                </div>
            </div>
            <div className='footer-bottom'>
                <div className='footer-copyright'>{'Copyright @ 2022 Jukoyaki.All Rights Reserved.'}</div>
            </div>
        </div>
    </div>
  )
}
