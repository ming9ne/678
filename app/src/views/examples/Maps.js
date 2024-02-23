
import React, { useEffect, useState } from "react";
import geojson from "../../data/geo3.json"; // 경로를 적절히 조정하세요
import { Map, MapMarker, MapTypeControl, ZoomControl, MapTypeId, CustomOverlayMap, MapInfoWindow } from "react-kakao-maps-sdk";
import { Button, ButtonGroup, Card, Container, Row, CardBody, CardHeader, CardImg, Col } from "reactstrap";
import Header from "components/Headers/Header.js";

const { kakao } = window;

function KakaoMapWithMarker() {
    const [map, setMap] = useState(null);
    const [parkData, setParkData] = useState([]);
    const [selectedPark, setSelectedPark] = useState(null);
    const [pollutionData, setPollutionData] = useState([]);
    const [mapType, setMapType] = useState("ROADMAP"); // 기본값은 일반 지도

    useEffect(() => {
        const fetchParkData = async () => {
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

        const fetchPollutionData = async () => {
            try {
                const response = await fetch(
                    "http://192.168.219.107:8080/api/v1/pollution-service/load"
                );
                if (response.ok) {
                    const data = await response.json();
                    setPollutionData(data);
                } else {
                    console.error("Failed to fetch pollution data");
                }
            } catch (error) {
                console.error("Error while fetching pollution data", error);
            }
        };

        fetchParkData();
        fetchPollutionData();
    }, []);

    useEffect(() => {
        if (parkData.length > 0 && pollutionData.length > 0) {
            const mapContainer = document.getElementById("pollution-map");
            const mapOption = {
                center: new kakao.maps.LatLng(37.566826, 126.9786567),
                level: 9,
            };

            const newMap = new kakao.maps.Map(mapContainer, mapOption);
            setMap(newMap);

            // 행정구역 표시 부분 수정
            let data = geojson.features;

            const customOverlay = new kakao.maps.CustomOverlay({});
            const infowindow = new kakao.maps.InfoWindow({ removable: true });

            const displayArea = (coordinates, areaData) => {
                let path = [];
                coordinates[0].forEach((coordinate) => {
                    path.push(new kakao.maps.LatLng(coordinate[1], coordinate[0]));
                });

                let fillColor = "#fff"; // Default color

                switch (areaData.grade) {
                    case "매우 나쁨":
                        fillColor = "#FF6666"; // Light Red
                        break;
                    case "나쁨":
                        fillColor = "#FFD1D1"; // Light Yellow
                        break;
                    case "보통":
                        fillColor = "#C0E4C9"; // Light Green
                        break;
                    case "좋음":
                        fillColor = "#B3CCFF"; // Light Blue
                        break;
                    case "점검중":
                        fillColor = "#EAEAEA"; // Light Gray
                        break;
                    default:
                        break;
                }

                let polygon = new kakao.maps.Polygon({
                    map: newMap,
                    path: path,
                    strokeWeight: 2,
                    strokeColor: "#004c80",
                    strokeOpacity: 0.8,
                    fillColor: fillColor,
                    fillOpacity: 0.7,
                });

                kakao.maps.event.addListener(polygon, "click", function (mouseEvent) {
                    const content =
                        `<div style="padding:2px;"><p><b>${areaData.msrName}</b></p>` +
                        `<p>환경지수 등급: ${areaData.grade}</p>` +
                        `<p>미세먼지: ${areaData.pm10}</p>` +
                        `<p>초미세먼지: ${areaData.pm25}</p></div>`;

                    infowindow.setContent(content);
                    infowindow.setPosition(mouseEvent.latLng);
                    infowindow.setMap(newMap);
                });
            };

            data.forEach((val) => {
                const coordinates = val.geometry.coordinates;
                const areaData = pollutionData.find(
                    (pollution) => pollution.msrName === val.properties.SIG_KOR_NM
                );

                if (areaData) {
                    displayArea(coordinates, areaData);
                }
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

                marker.setMap(newMap);

                kakao.maps.event.addListener(marker, "click", function () {
                    setSelectedPark(park);
                });
            });
        }
    }, [parkData, pollutionData]);

    // useEffect(() => {
    //     // 선택된 공원이 변경될 때 실행되는 효과
    //     if (selectedPark && map) {
    //         const content =
    //             `<div style="padding:2px;"><p><b>${selectedPark.parkName}</b></p>` +
    //             `<p>공원 주소: ${selectedPark.parkAddr}</p>` +
    //             `<p>전화번호: ${selectedPark.telNum}</p></div>`;
    //
    //         const infowindow = new kakao.maps.InfoWindow({ removable: true });
    //         infowindow.setContent(content);
    //         infowindow.setPosition(new kakao.maps.LatLng(selectedPark.parkLocY, selectedPark.parkLocX));
    //         infowindow.setMap(map);
    //     }
    // }, [selectedPark, map]);

    useEffect(() => {
        // 선택된 공원이 변경될 때 실행되는 효과
        if (selectedPark && map) {
            const content =
                `<div style="padding:2px; max-width: 300px;">` +
                `<p><b>${selectedPark.parkName}</b></p>` +
                `<p>공원 주소: ${selectedPark.parkAddr}</p>` +
                `<p>전화번호: ${selectedPark.telNum}</p>`+
                `<p><img src="${selectedPark.parkImg}" style="max-width:100%; height:auto;" alt="공원 이미지"></p>` +
                `</div>`;

            const infowindow = new kakao.maps.InfoWindow({ removable: true });
            infowindow.setContent(content);
            infowindow.setPosition(new kakao.maps.LatLng(selectedPark.parkLocY, selectedPark.parkLocX));
            infowindow.setMap(map);
        }
    }, [selectedPark, map]);





    return (
        <div>
            <div
                id="other-content"
                style={{
                    position: "absolute",
                    top: 0,
                    left: 0,
                    width: "90vw",
                    height: "20vh",
                    backgroundColor: "lightblue",
                }}
            >
                {/* 버튼 등 상단 컨텐츠 영역 */}
                {/*<button onClick={() => setMapType("ROADMAP")}>전체 지도</button>*/}
                {/*<button onClick={() => setMapType("TRAFFIC")}>권역별 지도</button>*/}
            </div>
            <div
                id="pollution-map"
                style={{
                    position: "absolute",
                    top: "20vh",
                    width: "90vw",
                    height: "70vh",
                }}
            ></div>
        </div>
    );
}

export default KakaoMapWithMarker;
