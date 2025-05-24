from googleapiclient.discovery import build
import isodate

API_KEY = ''
PLAYLIST_ID = 'PLbq5jHjpmq7q-Td2jOXtpf7SD5c53RqXh'
START_INDEX = 76   # 인덱스는 0부터 시작
END_INDEX = 81     # 8~10번째까지

youtube = build('youtube', 'v3', developerKey=API_KEY)

# 재생목록의 모든 영상 ID 가져오기
video_ids = []
next_page_token = None

while True:
    playlist_response = youtube.playlistItems().list(
        part='contentDetails',
        playlistId=PLAYLIST_ID,
        maxResults=50,
        pageToken=next_page_token
    ).execute()

    for item in playlist_response['items']:
        video_ids.append(item['contentDetails']['videoId'])

    next_page_token = playlist_response.get('nextPageToken')
    if not next_page_token:
        break

# 원하는 구간만 선택
target_video_ids = video_ids[START_INDEX:END_INDEX + 1]

# 영상 길이 합산
total_seconds = 0
for i in range(0, len(target_video_ids), 50):
    ids = target_video_ids[i:i+50]
    video_response = youtube.videos().list(
        part='contentDetails',
        id=','.join(ids)
    ).execute()

    for video in video_response['items']:
        duration = isodate.parse_duration(video['contentDetails']['duration'])
        total_seconds += int(duration.total_seconds())

# 출력
minutes, seconds = divmod(total_seconds, 60)
print(f'총 재생시간: {minutes}분 {seconds}초')
