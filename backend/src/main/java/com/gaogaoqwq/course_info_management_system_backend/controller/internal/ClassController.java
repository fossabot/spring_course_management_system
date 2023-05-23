package com.gaogaoqwq.course_info_management_system_backend.controller.internal;

import com.gaogaoqwq.course_info_management_system_backend.entity.internal.Class;
import com.gaogaoqwq.course_info_management_system_backend.exception.ParamException;
import com.gaogaoqwq.course_info_management_system_backend.exception.QueryException;
import com.gaogaoqwq.course_info_management_system_backend.response.R;
import com.gaogaoqwq.course_info_management_system_backend.response.ResultCode;
import com.gaogaoqwq.course_info_management_system_backend.service.interfaces.internal.ClassService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/class")
public class ClassController {

    private final ClassService classService;

    @PostMapping("/save")
    public R save(@RequestBody Class cls) throws SQLException, ParamException {
        Class c = classService.save(cls, cls.getFkMajor());
        return R.success().code(ResultCode.SUCCESS.getCode()).data(c);
    }

    @GetMapping("/all")
    public R fetchClasses() {
        return R.success().code(ResultCode.SUCCESS.getCode()).data(classService.findClassList());
    }

    @GetMapping("/query")
    public R query(@RequestParam @NotNull Map<String, String> params) {
        if (params.isEmpty()) {
            throw new ParamException("空参数", ResultCode.PARAM_ERROR.getCode());
        }

        Set<String> singleResultParams = Set.of("id", "code_name", "name");
        for (var param : params.keySet()) {
            if (singleResultParams.contains(param) && params.size() > 1) {
                throw new ParamException("查询参数冲突", ResultCode.PARAM_CONFLICT.getCode());
            }
        }

        if (params.containsKey("id")) {
            Long id = Long.valueOf(params.get("id"));
            Optional<Class> opt = classService.findClassById(id);
            return opt.map(student -> R.success().code(ResultCode.SUCCESS.getCode()).data(student))
                    .orElseThrow(() -> new QueryException("未找到参数对应的班级"));
        }
        if (params.containsKey("code_name")) {
            String codeName = params.get("code_name");
            Optional<Class> opt = classService.findClassByCodeName(codeName);
            return opt.map(student -> R.success().code(ResultCode.SUCCESS.getCode()).data(student))
                    .orElseThrow(() -> new QueryException("未找到参数对应的班级"));
        }
        if (params.containsKey("name")) {
            String name = params.get("name");
            Optional<Class> opt = classService.findClassByCodeName(name);
            return opt.map(student -> R.success().code(ResultCode.SUCCESS.getCode()).data(student))
                    .orElseThrow(() -> new QueryException("未找到参数对应的班级"));
        }

        Map<Class, Integer> classCnt = new HashMap<>();
        List<Class> result = new ArrayList<>();
        int paramCnt = 0;
        if (params.containsKey("major_id")) {
            paramCnt++;
            List<Class> classes = classService.findClassesByMajorId(Long.valueOf(params.get("major_id")));
            for (var cls : classes) {
                classCnt.put(cls, classCnt.get(cls) == null
                        ? 1
                        : classCnt.get(cls)+1);
            }
        }
        if (params.containsKey("major_name")) {
            paramCnt++;
            List<Class> classes = classService.findClassesByMajorName(params.get("major_name"));
            for (var cls : classes) {
                classCnt.put(cls, classCnt.get(cls) == null
                        ? 1
                        : classCnt.get(cls)+1);
            }
        }

        for (var cls : classCnt.keySet()) {
            if (classCnt.get(cls).equals(paramCnt)) {
                result.add(cls);
            }
        }

        return R.success().code(ResultCode.SUCCESS.getCode()).data(result);
    }

    @PutMapping("/update")
    public R update(@RequestBody Class cls) {
        Class c = classService.updateClass(cls);
        return R.success().code(ResultCode.SUCCESS.getCode()).data(c);
    }
}
