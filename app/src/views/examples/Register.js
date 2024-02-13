//
//
// import React, { useState } from 'react';
//
//
// const SignupForm = () => {
//   const [signupData, setSignupData] = useState({
//     username: '',
//     password: '',
//     email: '',
//     check_num: '',
//   });
//
//   const [emailAuth, setEmailAuth] = useState('');
//   const [isSignupDisabled, setSignupDisabled] = useState(true);
//
//   const sendAuthToMail = () => {
//     const { email } = signupData;
//
//     if (email === '') {
//       alert('인증가능한 이메일을 입력해주세요.');
//       return;
//     }
//
//     fetch(`http://localhost:8080/emailAuth?email=${email}`)
//         .then(response => response.json())
//         .then(data => {
//           console.log("인증번호 :", data);
//           alert('인증번호가 발송되었습니다.');
//           // 숫자로만 이루어진 문자열로 변환
//           setEmailAuth(String(data));
//         })
//         .catch(error => {
//           alert('메일 발송에 실패했습니다.');
//         });
//   };
//
//   const checkAuth = () => {
//     const { check_num } = signupData;
//     console.log('check_num:', check_num); // 디버깅을 위한 콘솔 메시지
//     if (check_num !== '' && check_num === emailAuth) {
//       alert('인증번호 확인완료');
//       setSignupDisabled(false);
//     } else {
//       alert('인증번호가 올바르지 않습니다.');
//       setSignupDisabled(true);
//     }
//   };
//
//   const handleInputChange = (e) => {
//     const { name, value } = e.target;
//     setSignupData(prevData => ({
//       ...prevData,
//       [name]: value,
//     }));
//   };
//
//   const handleSubmit = (e) => {
//     e.preventDefault();
//     console.log('Signup data submitted:', signupData);
//     // 여기서 서버로 회원가입 정보를 전송하도록 추가적인 로직을 구현할 수 있습니다.
//
//   };
//
//   return (
//       <div className="container">
//         <div className="py-5 text-center">
//           <h2>회원가입</h2>
//         </div>
//
//         <form onSubmit={handleSubmit}>
//           <div className="form-group">
//             <label htmlFor="username">아이디</label>
//             <input
//                 type="text"
//                 className="form-control"
//                 id="username"
//                 required
//                 onChange={handleInputChange}
//                 name="username"
//                 value={signupData.username}
//             />
//           </div>
//
//           <div className="form-group">
//             <label htmlFor="password">비밀번호</label>
//             <input
//                 type="password"
//                 className="form-control"
//                 id="password"
//                 required
//                 onChange={handleInputChange}
//                 name="password"
//                 value={signupData.password}
//             />
//           </div>
//
//           <div className="form-group">
//             <label htmlFor="email">이메일</label>
//             <div>
//               <input
//                   type="email"
//                   className="form-control"
//                   id="email"
//                   required
//                   onChange={handleInputChange}
//                   name="email"
//                   value={signupData.email}
//               />
//               <br />
//               <button type="button" className="btn btn-secondary" onClick={sendAuthToMail}>
//                 인증번호 발송
//               </button>
//             </div>
//           </div>
//
//           <div className="form-group">
//             <label htmlFor="check_num">인증번호</label>
//             <input
//                 type="text"
//                 className="form-control"
//                 id="check_num"
//                 required
//                 onChange={handleInputChange}
//                 name="check_num"
//                 value={signupData.check_num}
//             />
//             <br />
//             <button type="button" className="btn btn-secondary" onClick={checkAuth}>
//               인증번호 확인
//             </button>
//           </div>
//
//           <br />
//
//           <div className="form-group" style={{ textAlign: 'center' }}>
//             <button
//                 type="submit"
//                 className="btn btn-primary"
//                 style={{ display: 'inline-block' }}
//                 disabled={isSignupDisabled}
//             >
//               회원가입
//             </button>
//           </div>
//         </form>
//         <br />
//       </div>
//   );
// };
//
// export default SignupForm;

import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const SignupForm = () => {
  const navigate = useNavigate();

  const [signupData, setSignupData] = useState({
    username: '',
    password: '',
    email: '',
    check_num: '',
  });

  const [emailAuth, setEmailAuth] = useState('');
  const [isSignupDisabled, setSignupDisabled] = useState(true);

  const sendAuthToMail = () => {
    const { email } = signupData;

    if (email === '') {
      alert('인증가능한 이메일을 입력해주세요.');
      return;
    }

    fetch(`http://localhost:8080/emailAuth?email=${email}`)
        .then(response => response.json())
        .then(data => {
          console.log("인증번호 :", data);
          alert('인증번호가 발송되었습니다.');
          // 숫자로만 이루어진 문자열로 변환
          setEmailAuth(String(data));
        })
        .catch(error => {
          alert('메일 발송에 실패했습니다.');
        });
  };

  const checkAuth = () => {
    const { check_num } = signupData;
    console.log('check_num:', check_num); // 디버깅을 위한 콘솔 메시지
    if (check_num !== '' && check_num === emailAuth) {
      alert('인증번호 확인완료');
      setSignupDisabled(false);
    } else {
      alert('인증번호가 올바르지 않습니다.');
      setSignupDisabled(true);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setSignupData(prevData => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    // 회원가입 정보를 서버로 전송
    fetch('http://192.168.219.110:8080/signup', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(signupData),
    })
        .then(response => {
          if (!response.ok) {
            throw new Error('Network response was not ok');
          }
          return response.json();
        })
        .then(data => {
          console.log('Signup data submitted:', data);

          // 회원가입 성공 후 로그인 페이지로 이동
          navigate('/login');
        })
        .catch(error => {
          console.error('Error submitting signup data:', error);
          // 에러 처리 로직을 추가할 수 있습니다.
        });
  };

  return (
      <div className="container">
        <div className="py-5 text-center">
          <h2>회원가입</h2>
        </div>

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="username">아이디</label>
            <input
                type="text"
                className="form-control"
                id="username"
                required
                onChange={handleInputChange}
                name="username"
                value={signupData.username}
            />
          </div>

          <div className="form-group">
            <label htmlFor="password">비밀번호</label>
            <input
                type="password"
                className="form-control"
                id="password"
                required
                onChange={handleInputChange}
                name="password"
                value={signupData.password}
            />
          </div>

          <div className="form-group">
            <label htmlFor="email">이메일</label>
            <div>
              <input
                  type="email"
                  className="form-control"
                  id="email"
                  required
                  onChange={handleInputChange}
                  name="email"
                  value={signupData.email}
              />
              <br />
              <button type="button" className="btn btn-secondary" onClick={sendAuthToMail}>
                인증번호 발송
              </button>
            </div>
          </div>

          <div className="form-group">
            <label htmlFor="check_num">인증번호</label>
            <input
                type="text"
                className="form-control"
                id="check_num"
                required
                onChange={handleInputChange}
                name="check_num"
                value={signupData.check_num}
            />
            <br />
            <button type="button" className="btn btn-secondary" onClick={checkAuth}>
              인증번호 확인
            </button>
          </div>

          <br />

          <div className="form-group" style={{ textAlign: 'center' }}>
            <button
                type="submit"
                className="btn btn-primary"
                style={{ display: 'inline-block' }}
                disabled={isSignupDisabled}
            >
              회원가입
            </button>
          </div>
        </form>
        <br />
      </div>
  );
};

export default SignupForm;

