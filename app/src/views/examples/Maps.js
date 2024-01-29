
import React, { useEffect, useState } from "react";
import geojson from "../../data/geo3.json"; // 경로를 적절히 조정하세요

const { kakao } = window;

function KakaoMapWithMarker() {
    const [parkData, setParkData] = useState([]);
    const [selectedPark, setSelectedPark] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch(
                    "http://192.168.219.107:8080/api/v1/park-service/load"
                );
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
    }, []);

    useEffect(() => {
        if (parkData.length > 0) {
            const mapContainer = document.getElementById("pollution-map");
            const mapOption = {
                center: new kakao.maps.LatLng(37.566826, 126.9786567),
                level: 9,
            };

            const map = new kakao.maps.Map(mapContainer, mapOption);

            // 행정구역 표시 부분
            let data = geojson.features;
            let coordinates = [];
            let name = "";

            const customOverlay = new kakao.maps.CustomOverlay({});
            const infowindow = new kakao.maps.InfoWindow({ removable: true });

            const displayArea = (coordinates, name) => {
                let path = [];
                coordinates[0].forEach((coordinate) => {
                    path.push(new kakao.maps.LatLng(coordinate[1], coordinate[0]));
                });

                let polygon = new kakao.maps.Polygon({
                    map: map,
                    path: path,
                    strokeWeight: 2,
                    strokeColor: "#004c80",
                    strokeOpacity: 0.8,
                    fillColor: "#fff",
                    fillOpacity: 0.7,
                });

                kakao.maps.event.addListener(polygon, "mouseover", function (mouseEvent) {
                    polygon.setOptions({ fillColor: "#09f" });
                    customOverlay.setContent('<div class="area">' + name + "</div>");
                    customOverlay.setPosition(mouseEvent.latLng);
                    customOverlay.setMap(map);
                });

                kakao.maps.event.addListener(polygon, "mousemove", function (mouseEvent) {
                    customOverlay.setPosition(mouseEvent.latLng);
                });

                kakao.maps.event.addListener(polygon, "mouseout", function () {
                    polygon.setOptions({ fillColor: "#fff" });
                    customOverlay.setMap(null);
                });

                kakao.maps.event.addListener(polygon, "click", function (mouseEvent) {
                    const content =
                        '<div style="padding:2px;"><p><b>' +
                        name +
                        '</b></p><p>미세먼지: ' +
                        '</b></p><p>초미세먼지: ' +
                        "</div>";

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

            // 공원 마커 표시 부분
            parkData.forEach((park) => {
                const markerPosition = new kakao.maps.LatLng(
                    Number(park.parkLocY),
                    Number(park.parkLocX)
                );

                const marker = new kakao.maps.Marker({
                    position: markerPosition,
                });

                marker.setMap(map);

                kakao.maps.event.addListener(marker, "click", function () {
                    setSelectedPark(park);
                });
            });
        }
    }, [parkData]);

    useEffect(() => {
        // 선택된 공원이 변경될 때 실행되는 효과
        if (selectedPark) {
            // 여기에서 상세 정보를 표시하는 모달 또는 다른 방식으로 구현
            alert(
                `Selected Park: ${selectedPark.parkName}\nAddress: ${selectedPark.parkAddr}\nTel: ${selectedPark.telNum}`
            );
        }
    }, [selectedPark]);

    return (
        <div
            id="pollution-map"
            style={{
                width: "100vw",
                height: "100vh",
            }}
        ></div>
    );
}

export default KakaoMapWithMarker;
