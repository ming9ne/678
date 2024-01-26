import React, { useEffect } from "react";

const { kakao } = window;

function KakaoMapWithMarker() {
  useEffect(() => {
    // 카카오 맵 API 로드
    kakao.maps.load(() => {
      const container = document.getElementById('map');
      const options = {
        center: new kakao.maps.LatLng(37.615749261729, 127.01326329513556),
        level: 3
      };

      // 지도 생성
      const map = new kakao.maps.Map(container, options);

      // 마커 생성
      const markerPosition = new kakao.maps.LatLng(37.615749261729, 127.01326329513556);
      const marker = new kakao.maps.Marker({
        position: markerPosition
      });

      // 마커 지도에 표시
      marker.setMap(map);

      // 창 크기가 변경될 때 맵을 중앙에 유지
      const centerPosition = map.getCenter();
      const resizeEvent = kakao.maps.event.addListener(map, 'resize', function () {
        map.panTo(centerPosition);
      });

      // 컴포넌트가 언마운트될 때 이벤트 리스너 제거
      return () => {
        kakao.maps.event.removeListener(resizeEvent);
      };
    });
  }, []);

  return (
      <div id="map" style={{
        width: '100vw', // 전체 화면 가로
        height: '100vh', // 전체 화면 세로
      }}></div>
  );
}

export default KakaoMapWithMarker;
