import React, { useEffect } from "react";
import geojson from '../../data/geo3.json'; // 경로를 적절히 조정하세요

const { kakao } = window;

function KakaoMapWithMarker() {
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
