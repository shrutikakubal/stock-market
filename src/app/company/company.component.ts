import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { CompanyService } from '../service/company.service';
import { Company } from '../model/company.model';

@Component({
  selector: 'app-company',
  templateUrl: './company.component.html',
  styleUrls: ['./company.component.css']
})
export class CompanyComponent implements OnInit {
  companyArr: Array<Company> = [];
  updateFlag = false;

  companyForm = this.formBuilder.group({
    companyName: ['', Validators.required],
    turnover: ['', Validators.required],
    ceo: ['', Validators.required],
    boardOfDirectors: ['', Validators.required],
    stockExchange: ['', Validators.required],
    sectorName: ['', Validators.required],
    briefWriteup: ['', Validators.required],
    companyCode: ['', Validators.required],
    status: ['', Validators.required]
  });
  constructor(private formBuilder: FormBuilder, private CompanySrv: CompanyService) { }

  ngOnInit(): void {
    //this.refreshTable();
  }

  get companyName() {
    return this.companyForm.get('companyName');
  }
  get turnover() {
    return this.companyForm.get('turnover');
  }
  get ceo() {
    return this.companyForm.get('ceo');
  }
  get boardOfDirectors() {
    return this.companyForm.get('boardOfDirectors');
  }
  get stockExchange() {
    return this.companyForm.get('stockExchange');
  }
  get sectorName() {
    return this.companyForm.get('sectorName');
  }
  get briefWriteup() {
    return this.companyForm.get('briefWriteup');
  }
  get companyCode() {
    return this.companyForm.get('companyCode');
  }
  get status() {
    return this.companyForm.get('status');
  }

}
