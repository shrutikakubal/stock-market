import { Injectable } from '@angular/core';
import { FileUploader } from 'ng2-file-upload';
import { StorageService } from './storage.service';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UploadService {

  constructor(private storageSrv: StorageService) { }

  baseUrl: string = environment.getBaseUrl('upl');
  token: string = 'Bearer ' + this.storageSrv.getItem('token');

  getUploader(): FileUploader{
    return new FileUploader({
        url: this.baseUrl + 'upload/admin/uploadexcel',
        method: 'POST',
        itemAlias: 'excelsheet',
        authToken: this.token,
        authTokenHeader: 'Authorization',
        headers: [
          { name: 'customer-header', value: this.token }
        ]
    });
  }



}