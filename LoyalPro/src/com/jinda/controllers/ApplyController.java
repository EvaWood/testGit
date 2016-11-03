package com.jinda.controllers;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;
import com.jinda.common.DBHelperClass;
import com.jinda.models.*;

@Controller
@RequestMapping(value = "/apply")

public class ApplyController {
	@RequestMapping(value = { "/inout.do" }, method = RequestMethod.GET)
	public String unit(Model model) {
		return "/apply/inout";
	}
}
