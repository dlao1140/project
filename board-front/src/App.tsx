import React from 'react';
import './App.css';
import BoardItem from 'components/BoardItem';
import latestBoardListMock from 'mocks/latest-board-list.mock';
import Top3Item from 'components/Top3Item';
import top3BoardListMock from 'mocks/top-3-board-list.mock';
import CommentItem from 'components/CommentItem';
import commentListMock from 'mocks/comment-list.mock';
import favoriteListMock from 'mocks/favorite-list.mock';
import FavoriteItem from 'components/FavoriteItem';
import InputBox from 'components/InputBox';


function App() {
  return (
    <>
      {//{latestBoardListMock.map((boardListItem, index) => <BoardItem boardListItem={boardListItem} />)}
      }

      {//<div style={{ display: 'flex', justifyContent: 'center', gap: '24px' }}>
        //{top3BoardListMock.map(top3ListItem => <Top3Item top3ListItem={top3ListItem} />)}
     //</div>
      }
      {//<div style ={{ padding: '0 20px', display: 'flex', flexDirection: 'column', gap: '30px' }}>
      //</div>
      //{commentListMock.map((commentListItem, index) => <CommentItem commentListItem={commentListItem} />)}
      }
      {//<div style ={{ display: 'flex', flexWrap: 'wrap',columnGap: '30px', rowGap: '20px'}}>
      //{favoriteListMock.map(favoriteListItem => <FavoriteItem favoriteListItem={favoriteListItem} />)}
      //</div>
      }
      <InputBox />
    </>
  );
}

export default App;
