-- phpMyAdmin SQL Dump
-- version 3.1.1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tempo de Geração: Abr 01, 2014 as 01:54 PM
-- Versão do Servidor: 5.1.30
-- Versão do PHP: 5.2.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Banco de Dados: `gap`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `arvore`
--

CREATE TABLE IF NOT EXISTS `arvore` (
  `id_arvore` int(5) NOT NULL AUTO_INCREMENT,
  `no_pai` int(5) DEFAULT NULL,
  `no_filho` int(5) DEFAULT NULL,
  `codigo` varchar(70) DEFAULT NULL,
  PRIMARY KEY (`id_arvore`),
  KEY `no_fk` (`no_filho`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;



-- --------------------------------------------------------

--
-- Estrutura da tabela `configuraremail`
--

CREATE TABLE IF NOT EXISTS `configuraremail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `servidor` varchar(45) NOT NULL,
  `porta` varchar(5) NOT NULL,
  `autenticacao` varchar(5) NOT NULL,
  `usuario` varchar(60) NOT NULL,
  `senha` varchar(35) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Extraindo dados da tabela `configuraremail`
--

INSERT INTO `configuraremail` (`id`, `servidor`, `porta`, `autenticacao`, `usuario`, `senha`) VALUES
(1, '150.163.73.157', '25', 'true', 'livia.miura@inpe.br', 'sbXM1DX@');

-- --------------------------------------------------------

--
-- Estrutura da tabela `documentovinculado`
--

CREATE TABLE IF NOT EXISTS `documentovinculado` (
  `id_DocumentoVinculado` int(5) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(500) DEFAULT NULL,
  `titulo` varchar(100) DEFAULT NULL,
  `no_fk` int(5) NOT NULL,
  `idItemdono` int(5) DEFAULT NULL,
  `tipoDocumento_fk` int(5) DEFAULT NULL,
  `estadoDocumento` varchar(45) DEFAULT NULL,
  `data_alteracao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `template` varchar(145) DEFAULT NULL,
  `codigoExterno` varchar(45) DEFAULT NULL,
  `codigoDocumento` varchar(45) DEFAULT NULL,
  `codigoProduto` varchar(70) DEFAULT NULL,
  `noPai` int(5) DEFAULT NULL,
  `autores` varchar(145) DEFAULT NULL,
  `status` int(5) DEFAULT NULL,
  `diretorio` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id_DocumentoVinculado`),
  KEY `no_fk` (`no_fk`),
  KEY `tipoDeDocumento_fk` (`tipoDocumento_fk`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;


-- --------------------------------------------------------

--
-- Estrutura da tabela `estadono`
--

CREATE TABLE IF NOT EXISTS `estadono` (
  `id_estadoNo` int(5) NOT NULL AUTO_INCREMENT,
  `identificacao` varchar(300) DEFAULT NULL,
  `descricao` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_estadoNo`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=12 ;

--
-- Extraindo dados da tabela `estadono`
--

INSERT INTO `estadono` (`id_estadoNo`, `identificacao`, `descricao`) VALUES
(1, 'Especificação', 'Fase de Especificação do Projeto'),
(2, 'Desenvolvimento', 'Projeto em Fase de Desenvolvimento'),
(3, 'Teste', 'Projeto em fase de Teste'),
(4, 'Pronto', 'Projeto Finalizado'),
(5, 'Entregue', 'Projeto entregue para Cliente'),
(6, 'Interrompido', 'Projeto Interrompido'),
(7, 'Fabricação', 'Projeto em fase de Fabricação'),
(8, 'Integração', 'Fase de Integração do Projeto'),
(10, 'Definido', 'Projeto Definido'),
(11, 'Não se Aplica ao Nível 0 e 1', 'No nó de nível 0 e 1 da árvore produto');

-- --------------------------------------------------------

--
-- Estrutura da tabela `historico`
--

CREATE TABLE IF NOT EXISTS `historico` (
  `id_Historico` int(5) NOT NULL AUTO_INCREMENT,
  `usuario_fk` int(3) NOT NULL,
  `data_entrada` datetime DEFAULT NULL,
  `data_saida` datetime DEFAULT NULL,
  PRIMARY KEY (`id_Historico`),
  KEY `usuario_fk` (`usuario_fk`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;




-- --------------------------------------------------------

--
-- Estrutura da tabela `historicodocvinc`
--

CREATE TABLE IF NOT EXISTS `historicodocvinc` (
  `id_HistDocVinc` int(5) NOT NULL AUTO_INCREMENT,
  `documentoVinculado_fk` int(5) NOT NULL,
  `tipoAlteracao` varchar(45) DEFAULT NULL,
  `usuarioOperacao` varchar(45) NOT NULL,
  `data_operacao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_HistDocVinc`),
  KEY `documentoVinculado_fk` (`documentoVinculado_fk`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;



-- --------------------------------------------------------

--
-- Estrutura da tabela `historicono`
--

CREATE TABLE IF NOT EXISTS `historicono` (
  `id_historicoNo` int(5) NOT NULL AUTO_INCREMENT,
  `data_operacao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `operacao` varchar(60) DEFAULT NULL,
  `no_fk` int(5) NOT NULL,
  `usuario_fk` int(3) NOT NULL,
  `estadoNo` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id_historicoNo`),
  KEY `no_fk` (`no_fk`),
  KEY `usuario_fk` (`usuario_fk`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;



-- --------------------------------------------------------

--
-- Estrutura da tabela `itemdono`
--

CREATE TABLE IF NOT EXISTS `itemdono` (
  `id_ItemDoNo` int(5) NOT NULL AUTO_INCREMENT,
  `data_ItemDoNo` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `no_fk` int(5) NOT NULL,
  `documentoVinculado_fk` int(3) NOT NULL,
  `status` int(5) NOT NULL,
  PRIMARY KEY (`id_ItemDoNo`),
  KEY `no_fk` (`no_fk`),
  KEY `documentoVinculado_fk` (`documentoVinculado_fk`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;



-- --------------------------------------------------------

--
-- Estrutura da tabela `no`
--

CREATE TABLE IF NOT EXISTS `no` (
  `id_No` int(5) NOT NULL AUTO_INCREMENT,
  `titulo` varchar(150) DEFAULT NULL,
  `descricao` varchar(350) DEFAULT NULL,
  `responsavel` int(3) DEFAULT NULL,
  `produtoFinal` tinyint(4) DEFAULT NULL,
  `tipoProduto_fk` int(5) DEFAULT NULL,
  `no_fk` int(5) DEFAULT NULL,
  `nivel` int(2) DEFAULT NULL,
  `diretorio` varchar(150) DEFAULT NULL,
  `codigo` varchar(70) DEFAULT NULL,
  `identExterna` varchar(70) DEFAULT NULL,
  `estadoNo_fk` int(5) DEFAULT NULL,
  `data_criacao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `tipoDominio` varchar(45) DEFAULT NULL,
  `status` int(5) DEFAULT NULL,
  PRIMARY KEY (`id_No`),
  KEY `estadoNo_fk` (`estadoNo_fk`),
  KEY `tipoProduto_fk` (`tipoProduto_fk`),
  KEY `no_fk` (`no_fk`),
  KEY `usuario_fk` (`responsavel`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;


-- --------------------------------------------------------

--
-- Estrutura da tabela `tipodocumento`
--

CREATE TABLE IF NOT EXISTS `tipodocumento` (
  `id_TipoDocumento` int(5) NOT NULL AUTO_INCREMENT,
  `identificacao` varchar(45) DEFAULT NULL,
  `tipoDocumento` varchar(45) DEFAULT NULL,
  `descricao` varchar(245) DEFAULT NULL,
  `template` varchar(145) DEFAULT NULL,
  PRIMARY KEY (`id_TipoDocumento`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=12 ;

--
-- Extraindo dados da tabela `tipodocumento`
--

INSERT INTO `tipodocumento` (`id_TipoDocumento`, `identificacao`, `tipoDocumento`, `descricao`, `template`) VALUES
(1, 'RQ', 'RQ - DOCUMENTO DE REQUISITO', 'Especifica os requisitos de um sistema/subsistema/software. Especifica os requisitos de interface para um ou mais sistema.', 'instalar o linux na vm para bia.pdf'),
(2, 'PR', 'PR - DOCUMENTO DE PROJETO', 'Projeto de um sistema/subsistema/, projeto de arquitetura, linha basica para desenvolvimento de sistema.', 'dsstemplate-software requirement specification.doc'),
(3, 'BD', 'BD - DOCUMENTO DO PROJETO DA BASE DE DADOS', 'Projeto da base de dados, dados relacionados, sistema de gerenciamento de base de dados para acesso, arquivos', 'dsstemplate-projeto da base de dados.doc'),
(4, 'IF', 'IF - DOCUMENTO DE INTERFACE', 'Descricao das interfaces internas e externas entre sistemas, subsistemas, componentes de hardware e componentes de software.', ''),
(5, 'NA', 'NA - NÃO SE APLICA', 'Não se aplica', ''),
(6, 'PD', 'PD - DOCUMENTO DE PROJETO DETALHADO', 'Projeto de sistema/subsistema, projeto da arquitetura,linha básica para desenvolvimento do sistema. Decisões de projeto de itens de software , projeto arquitetura, projeto preliminar. Decisões de projeto de interface de comunicação de controle', 'dsstemplate-projeto detalhado subsistema nao oo.doc'),
(7, 'MN', 'MN - DOCUMENTO DE OPERAÇÃO ', 'Tem como finalidade mostrar como usar o produto de software e como instalar. Podendo ser:\r\nManual de usuário\r\nManual de Instalação e Configuração\r\nDocumento de Manutenção', 'plano de integraã§ã£o e teste do subsistema - template.doc'),
(8, 'PG', 'PG - PLANO GERENCIAL', 'Documentos relacionados com atividades de suporte(gerenciamento de configuração, verificação e validação)', 'plano de desenvolvimento - template.doc');


-- --------------------------------------------------------

--
-- Estrutura da tabela `tipoproduto`
--

CREATE TABLE IF NOT EXISTS `tipoproduto` (
  `id_TipoProduto` int(5) NOT NULL AUTO_INCREMENT,
  `identificacao` varchar(45) DEFAULT NULL,
  `descricao` varchar(245) DEFAULT NULL,
  `observacao` text,
  PRIMARY KEY (`id_TipoProduto`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;

--
-- Extraindo dados da tabela `tipoproduto`
--

INSERT INTO `tipoproduto` (`id_TipoProduto`, `identificacao`, `descricao`, `observacao`) VALUES
(1, 'EQ', 'EQ - Equipamento de Hardware', 'Nao importando se o mesmo internamente e constituido de hardware ou software'),
(2, 'PG', 'PG - Programa', 'Programa executavel, script, compilado, interpretado, programa de instalacao , etc...'),
(3, 'SL', 'SL - Biblioteca Estatica', 'Uma Biblioteca estatica de Programas'),
(5, 'DB', 'BD - Base de Dados', 'Base de dados'),
(6, 'DC', 'DC - Documento Especifico de Projeto', 'Documento Gerado, podendo ser um documento especifico de projeto(ou seja, desenvolvimento do projeto nao e responsabilidade da divisao), relatorio tecnico, um procedimento operacional, um formulario de aatendimento, um artigo.'),
(7, 'DL', 'DL - Biblioteca dinâmica de programas', 'Biblioteca dinâmica de programas'),
(8, 'NA', 'NA - Não se aplica', 'Não se aplica');

-- --------------------------------------------------------

--
-- Estrutura da tabela `unidade`
--

CREATE TABLE IF NOT EXISTS `unidade` (
  `id_unidade` int(5) NOT NULL AUTO_INCREMENT,
  `nome_unidade` varchar(45) DEFAULT NULL,
  `localidade` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_unidade`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=28 ;

--
-- Extraindo dados da tabela `unidade`
--

INSERT INTO `unidade` (`id_unidade`, `nome_unidade`, `localidade`) VALUES
(1, 'DSS', 'São José dos Campos'),
(2, 'DEA', 'São José dos Campos'),
(3, 'DMC', 'São José dos Campos'),
(4, 'DSE', 'São José dos Campos'),
(5, 'SGP', 'São José dos Campos'),
(6, 'SCI', 'São José dos Campos'),
(7, 'SMF', 'São José dos Campos'),
(8, 'SMD', 'São José dos Campos'),
(9, 'LIT', 'São José dos Campos'),
(10, 'CTO', 'São José dos Campos'),
(11, 'SID', 'São José dos Campos'),
(12, 'DIR', 'São José dos Campos'),
(13, 'SPG', 'São José dos Campos'),
(14, 'CST', 'São José dos Campos'),
(15, 'CCR', 'São José dos Campos'),
(16, 'SAU', 'São José dos Campos'),
(17, 'CRN', 'São José dos Campos'),
(18, 'STI', 'São José dos Campos'),
(19, 'LAP', 'São José dos Campos'),
(20, 'LAS', 'São José dos Campos'),
(21, 'DGE', 'São José dos Campos'),
(22, 'OBT', 'São José dos Campos'),
(23, 'URC', 'Cachoeira Paulista'),
(24, 'CPT', 'Cachoeira Paulista'),
(25, 'DSA', 'Cachoeira Paulista'),
(26, 'LCP', 'Cachoeira Paulista'),
(27, 'DOP', 'Cachoeira Paulista');

-- --------------------------------------------------------

--
-- Estrutura da tabela `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
  `id_Usuario` int(3) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `predio` varchar(45) DEFAULT NULL,
  `ramal` int(4) DEFAULT NULL,
  `login` varchar(45) DEFAULT NULL,
  `senha` varchar(45) DEFAULT NULL,
  `perfil` varchar(60) NOT NULL,
  `unidade_fk` int(5) DEFAULT NULL,
  `status` int(5) DEFAULT NULL,
  `statusImagem` varchar(70) DEFAULT NULL,
  PRIMARY KEY (`id_Usuario`),
  UNIQUE KEY `login` (`login`),
  KEY `unidade_fk` (`unidade_fk`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=36 ;

--
-- Extraindo dados da tabela `usuario`
--

INSERT INTO `usuario` (`id_Usuario`, `nome`, `email`, `predio`, `ramal`, `login`, `senha`, `perfil`, `unidade_fk`, `status`, `statusImagem`) VALUES
(1, 'Lívia Miura', 'livia.miura@inpe.br', '1234788', 1234, '123', '123', 'Administrador', 6, 0, '/imagens/ativo4.png'),
(5, 'Antonio Cassiano', 'livia.miura@inpe.br', 'Beta', 6587, '001', '001', 'Usuário Interno - Gestor', 1, 0, '/imagens/ativo4.png'),
(34, 'Luciana Seda Cardoso', 'livia.miura@inpe.br', 'Beta', 6587, '555', '555', 'Usuário Interno - Gerente', 1, 0, '/imagens/ativo4.png'),
(35, 'Marcos Castro', 'marcos.castro@inpe.br', 'Beta', 6592, '002', '0002', 'Usuário Interno', 1, 0, '/imagens/ativo4.png');

--
-- Restrições para as tabelas dumpadas
--

--
-- Restrições para a tabela `documentovinculado`
--
ALTER TABLE `documentovinculado`
  ADD CONSTRAINT `tipoDocumento_fk` FOREIGN KEY (`tipoDocumento_fk`) REFERENCES `tipodocumento` (`id_TipoDocumento`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Restrições para a tabela `historico`
--
ALTER TABLE `historico`
  ADD CONSTRAINT `usuario_fk` FOREIGN KEY (`usuario_fk`) REFERENCES `usuario` (`id_Usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Restrições para a tabela `historicodocvinc`
--
ALTER TABLE `historicodocvinc`
  ADD CONSTRAINT `historicodocvinc_ibfk_2` FOREIGN KEY (`documentoVinculado_fk`) REFERENCES `documentovinculado` (`id_DocumentoVinculado`);

--
-- Restrições para a tabela `historicono`
--
ALTER TABLE `historicono`
  ADD CONSTRAINT `historicono_ibfk_1` FOREIGN KEY (`usuario_fk`) REFERENCES `usuario` (`id_Usuario`);

--
-- Restrições para a tabela `itemdono`
--
ALTER TABLE `itemdono`
  ADD CONSTRAINT `itemDoNo_ibfk_2` FOREIGN KEY (`documentoVinculado_fk`) REFERENCES `documentovinculado` (`id_DocumentoVinculado`);

--
-- Restrições para a tabela `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `unidade_fk` FOREIGN KEY (`unidade_fk`) REFERENCES `unidade` (`id_unidade`) ON DELETE NO ACTION ON UPDATE NO ACTION;
