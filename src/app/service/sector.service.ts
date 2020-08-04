import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Company } from '../model/company.model';
import { Observable } from 'rxjs/index';
import { Sector } from '../model/sector.model';


@Injectable({
  providedIn: 'root'
})
export class SectorService {

    constructor(private http: HttpClient) { }

  baseUrl: string = environment.getBaseUrl('sec');

  newSector(sector: Sector): Observable<Sector> {
    return this.http.post<Sector>(this.baseUrl + 'sector/admin/new', sector);
  }

  updateSectorByName(sectorName: string, sector: Sector): Observable<Sector> {
    if (!sectorName) { return new Observable(); }
    return this.http.put<Sector>(this.baseUrl + 'sector/admin/update/' + sectorName, sector);
  }

  getAllSectors(): Observable<Sector[]> {
    return this.http.get<Sector[]>(this.baseUrl + 'sector/all');
  }

  getSectorByName(sectorName: string): Observable<Sector> {
    if (!sectorName) { return new Observable(); }
    return this.http.get<Sector>(this.baseUrl + 'sector/' + sectorName);
  }

  findCompaniesBySectorName(sectorName: string): Observable<Company[]> { 
    if (!sectorName) { return new Observable(); }
    return this.http.get<Company[]>(this.baseUrl + 'sector/find/' + sectorName + '/companyList');
  }

  findSectorPrice(sectorName: String): Observable<Sector[]> {
    return this.http.get<Sector[]>(this.baseUrl + 'sector/sectorPrice/'+sectorName);
  }

  findSectorPriceBetween(sectorName: String, params:object): Observable<Sector[]> {
    return this.http.post<Sector[]>(this.baseUrl + 'sector/sectorPriceBetween/'+sectorName, params);
  }
}
