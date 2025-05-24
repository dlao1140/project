import Header from 'layouts/Header';
import Footer from 'layouts/Footer';
import { Outlet, useLocation } from 'react-router-dom';
import { AUTH_PATH } from 'constant';

//         component: 레이아웃      //
export default function COntainer() {
  //         state: 현재 페이지의 path name 상태를 가져옴   //
  const { pathname }= useLocation();


  //         render: 레이아웃 렌더링링      //
  return (
    <>
        {/* {pathname} */}
        <Header />
        <Outlet />
        <Footer />
        {/* {pathname !== AUTH_PATH() && <Footer />} */}

    </>
  )
}
