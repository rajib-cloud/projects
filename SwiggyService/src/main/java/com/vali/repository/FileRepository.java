package com.vali.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vali.entity.FileData;

public interface FileRepository extends JpaRepository<FileData, Long>{

}
