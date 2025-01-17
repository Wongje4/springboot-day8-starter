package com.rest.springbootemployee;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {
    @Mock
    CompanyRepository companyRepository;

    @InjectMocks
    CompanyService companyService;

    @Test
    void should_return_all_company_when_find_all_given_companies() {
        // given
        List<Company> companies = new ArrayList<>();
        Company company = new Company(1, "spring", null);
        companies.add(company);
        when(companyRepository.findAll()).thenReturn(companies);
        // when
        List<Company> results = companyService.findAll();
        // then
        assertThat(results, hasSize(1));
        assertEquals(company, results.get(0));
        verify(companyRepository).findAll();
    }

    @Test
    void should_update_name_only_when_update_through_service_given_companies() {
        // given
        int companyId = 1;
        Company company = new Company(companyId, "company1", null);
        Company toUpdateCompany = new Company(companyId, "company1_update", null);
        when(companyRepository.findById(companyId)).thenReturn(company);
        // when & then
        Company updatedCompany = companyService.update(company.getId(), toUpdateCompany);
        verify(companyRepository).findById(company.getId());
        assertEquals(toUpdateCompany.getName(), updatedCompany.getName());
    }

    @Test
    void should_get_company_by_id_when_perform_get_by_id_through_service_given_companies() {
        // given
        int companyId = 1;
        Company company = new Company(companyId, "company1", null);
        when(companyRepository.findById(company.getId())).thenReturn(company);
        // when
        Company company_s = companyService.findById(company.getId());
        // then

        verify(companyRepository).findById(company_s .getId());
        assertEquals(company_s.getName(), company.getName());
        assertEquals(company_s.getEmployees(), company.getEmployees());

    }

    @Test
    void should_get_company_by_page_and_pageSize_when_perform_get_by_page_and_pageSize_through_service_given_companies_and_page_and_pageSize() {
        //given
        List<Company> companies = new ArrayList<>();
        Company company1 = new Company(1, "company1", null);
        Company company2 = new Company(2, "company2", null);
        companies.add(company1);
        companies.add(company2);

        when(companyRepository.findByPage(1, 2)).thenReturn(companies);

        //when
        List<Company> results = companyService.findByPage(1,2);

        //then
        verify(companyRepository).findByPage(1,2);
        assertThat(results, hasSize(2));
        assertEquals(company1, results.get(0));
        assertEquals(company2, results.get(1));

    }

    @Test
    void should_add_company_when_perform_through_service_add_given_companies() {
        //given
        Company company1 = new Company(1, "company1", null);

        when(companyRepository.create(company1)).thenReturn(company1);

        //when
        Company result = companyService.create(company1);

        //then
        verify(companyRepository).create(company1);
        assertEquals(company1, result);

    }

    @Test
    void should_delete_company_when_perform_delete_given_companies() {
        // given
        int companyId = 1;
        Company company1 = new Company(companyId, "company1", null);
        when(companyRepository.findById(companyId)).thenReturn(company1);
        // when
        companyService.delete(companyId);
        // then
        verify(companyRepository).delete(companyId);
        List<Company> newCompanies = companyRepository.findAll();
        assertThat(newCompanies, hasSize(0));
    }

}
