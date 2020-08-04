// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

// fsdms-zuul-srv: /               - http://127.0.0.1:8050/
// fsdms-upload-srv: /upload-service/**       - http://127.0.0.1:8084/
// fsdms-exchange-srv: /exchange-service/**     - http://127.0.0.1:8081/
// fsdms-company-srv: /company-service/**      - http://127.0.0.1:8080/
// fsdms-sector-srv: /sector-service/**       - http://127.0.0.1:8082/
// fsdms-security-srv: /users-service/**     - http://127.0.0.1:8083/

function getBaseUrl(val: string): string {
  const gateSwitch = false; // gate switch between gateway and service directly

  let baseUrl: string;
  const baseGateWay = 'http://127.0.0.1:8050/'; //zuul

  switch (val) {
    case 'upl':
      if (gateSwitch) {
        baseUrl = baseGateWay + 'upload-service/';
      } else {
        baseUrl = 'http://127.0.0.1:8084/';
      }
      break;
    case 'exc':
      if (gateSwitch) {
        baseUrl = baseGateWay + 'exchange-service/';
      } else {
        baseUrl = 'http://127.0.0.1:8081/';
      }
      break;
    case 'com':
      if (gateSwitch) {
        baseUrl = baseGateWay + 'company-service/';
      } else {
        baseUrl = 'http://127.0.0.1:8080/';
      }
      break;
    case 'sec':
      if (gateSwitch) {
        baseUrl = baseGateWay + 'sector-service/';
      } else {
        baseUrl = 'http://127.0.0.1:8082/';
      }
      break;
    case 'usr':
      if (gateSwitch) {
        baseUrl = baseGateWay + 'users-service/';
      } else {
        baseUrl = 'http://127.0.0.1:8083/';
      }
      break;
    default:
      baseUrl = 'http://127.0.0.1/';
  }
  return baseUrl;
}

export const environment = {
  production: false,
  debug: false,
  getBaseUrl
};

