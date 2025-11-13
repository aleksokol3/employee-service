package su.aleksokol3.employeeservice.model.api.mapper;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import su.aleksokol3.employeeservice.model.entity.Employee;
import su.aleksokol3.employeeservice.util.DataUtils;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {BaseMapperImpl.class})
@RequiredArgsConstructor
class BaseMapperTest {

    private final BaseMapper baseMapper;

    @Test
    void entityToId() {
        // given
        Employee entity = DataUtils.getJuanRodriguezPersisted();
        // when
        UUID id = baseMapper.entityToId(entity);
        // then
        assertEquals(entity.getId(), id);
    }
}