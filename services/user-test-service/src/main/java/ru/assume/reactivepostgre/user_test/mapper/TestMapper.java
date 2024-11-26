package ru.assume.reactivepostgre.user_test.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.assume.reactivepostgre.user_test.model.TestDomain;
import ru.assume.reactivepostgre.user_test.model.TestDomainShort;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TestMapper {

    TestDomain domainShortToDomain(TestDomainShort domainShort);
}
