/* 处理了上传文件的同时也需要获得表单内地参数 */
/* HTML表单不做额外处理 */
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Configure a repository (to ensure a secure temp location is used)
		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		// Create a new file upload handler
		ServletFileUpload uploader = new ServletFileUpload(factory);

		List<FileItem> items = new ArrayList<FileItem>();
		// Parse the request
		try {
			items = uploader.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

		Iterator<FileItem> it = items.iterator();
		File uploadFile = null;
		String questionnaireId = "";

		while (it.hasNext()) {
			FileItem fileItem = (FileItem) it.next();

			if (!fileItem.isFormField()) {
				String fileName = fileItem.getName();
				uploadFile = new File(repository.getAbsolutePath() + File.separator + fileName);
				try {
					// 写文件到本地
					// write upload file to local file system
					fileItem.write(uploadFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.print(new String(fileItem.getString("UTF-8")));
                if ("impQnrId".equals(fileItem.getFieldName())) {
                	questionnaireId = new String(fileItem.getString("UTF-8"));
                }
			}
		}
//		return;
		
		// 读取文件测试
		// test read file
		List<QuestionDTO> list = readFile(uploadFile, questionnaireId);
		questionBo = new QuestionBoImpl();
		for (QuestionDTO dto : list) {
			questionBo.addQuestion(dto);
		}
	}