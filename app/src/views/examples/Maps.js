import React, {useEffect, useState} from "react";
import geojson from '../../data/geo3.json'; // 경로를 적절히 조정하세요

const { kakao } = window;

function KakaoMapWithMarker() {

    const [parkData, setParkData] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch("http://192.168.219.107:8080/api/v1/park-service/load");
                if (response.ok) {
                    const data = await response.json();
                    setParkData(data);
                } else {
                    console.error("Failed to fetch park data");
                }
            } catch (error) {
                console.error("Error while fetching park data", error);
            }
        };

        fetchData();
    }, []); // 빈 배열을 전달하여 컴포넌트가 마운트된 후 한 번만 실행되도록 함

    useEffect(() => {
        if (parkData.length > 0) {
            const mapContainer = document.getElementById('pollution-map');
            const mapOption = {
                center: new kakao.maps.LatLng(37.566826, 126.9786567),
                level: 9,
            };

            const map = new kakao.maps.Map(mapContainer, mapOption);

            parkData.forEach((park) => {
                // park 데이터를 사용하여 마커를 지도에 표시하는 로직
                const markerPosition = new kakao.maps.LatLng(Number(park.parkLocY), Number(park.parkLocX));

                // 마커 이미지를 생성
                const markerImage = new kakao.maps.MarkerImage(
                    park.parkImg, // 마커 이미지 URL
                    new kakao.maps.Size(50, 50), // 마커 이미지 크기
                    { offset: new kakao.maps.Point(25, 50) } // 마커 이미지의 중심 좌표
                );

                // 마커를 생성
                const marker = new kakao.maps.Marker({
                    position: markerPosition,
                    image: markerImage, // 마커 이미지 설정
                });

                // 마커를 지도에 표시
                marker.setMap(map);

                // 마커 클릭 이벤트 리스너
                kakao.maps.event.addListener(marker, 'click', function () {
                    // 클릭한 마커에 대한 정보를 인포윈도우에 표시
                    const infowindow = new kakao.maps.InfoWindow({
                        content: `<div><b>${park.parkName}</b><br>주소: ${park.parkAddr}<br>전화번호: ${park.telNum}</div>`,
                    });
                    infowindow.open(map, marker);
                });
            });
        }
    }, [parkData]);

    ///////////////////////////////////////////////////////////////////

    useEffect(() => {
        let data = geojson.features;
        let coordinates = [];
        let name = '';
        const mapContainer = document.getElementById('pollution-map');
        const mapOption = {
            center: new kakao.maps.LatLng(37.566826, 126.9786567),
            level: 9,
        };

        const map = new kakao.maps.Map(mapContainer, mapOption);
        const customOverlay = new kakao.maps.CustomOverlay({});
        const infowindow = new kakao.maps.InfoWindow({ removable: true });

        const displayArea = (coordinates, name) => {
            let path = [];
            let points = [];

            // pollution 변수를 사용하는 부분 삭제

            coordinates[0].forEach((coordinate) => {
                let point = {};
                point.x = coordinate[1];
                point.y = coordinate[0];
                points.push(point);
                path.push(new kakao.maps.LatLng(coordinate[1], coordinate[0]));
            });

            let polygon = new kakao.maps.Polygon({
                map: map,
                path: path,
                strokeWeight: 2,
                strokeColor: '#004c80',
                strokeOpacity: 0.8,
                fillColor: '#fff',
                fillOpacity: 0.7,
            });

            kakao.maps.event.addListener(polygon, 'mouseover', function (mouseEvent) {
                polygon.setOptions({ fillColor: '#09f' });
                customOverlay.setContent('<div class="area">' + name + '</div>');
                customOverlay.setPosition(mouseEvent.latLng);
                customOverlay.setMap(map);
            });

            kakao.maps.event.addListener(polygon, 'mousemove', function (mouseEvent) {
                customOverlay.setPosition(mouseEvent.latLng);
            });

            kakao.maps.event.addListener(polygon, 'mouseout', function () {
                polygon.setOptions({ fillColor: '#fff' });
                customOverlay.setMap(null);
            });

            kakao.maps.event.addListener(polygon, 'click', function (mouseEvent) {
                // pollution 변수를 사용하는 부분 삭제

                const content =
                    '<div style="padding:2px;"><p><b>' +
                    name +
                    '</b></p><p>미세먼지: ' +
                    '</b></p><p>초미세먼지: ' +
                    '</div>';

                infowindow.setContent(content);
                infowindow.setPosition(mouseEvent.latLng);
                infowindow.setMap(map);
            });
        };

        data.forEach((val) => {
            coordinates = val.geometry.coordinates;
            name = val.properties.SIG_KOR_NM;
            displayArea(coordinates, name);
        });
    }, []);

    return (
        <div id="pollution-map" style={{
            width: '100vw',
            height: '100vh',
        }}></div>
    );
}

export default KakaoMapWithMarker;
